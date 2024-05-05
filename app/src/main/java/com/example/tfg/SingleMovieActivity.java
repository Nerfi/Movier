package com.example.tfg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.dao.MovieDao;
import com.example.tfg.entity.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SingleMovieActivity extends AppCompatActivity {

private TextView singlemovieName, singleMovieRating, singleMovieDescrip;
private RatingBar singleMovieRatingBar;
private CheckBox deleteMovie, updateMovie;
//init db
AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_movie);
        db = AppDatabase.getDatabase(this);

        //init the UI
        singlemovieName = findViewById(R.id.movie_name);
        singleMovieRating = findViewById(R.id.movie_rating);
        singleMovieDescrip = findViewById(R.id.movie_description);
        singleMovieRatingBar = findViewById(R.id.ratingBar);
        deleteMovie = findViewById(R.id.checkBoxDelete);
        updateMovie = findViewById(R.id.checkBoxUpdate);

        //LLAMAR A LOS ELEMENTOS DEL NAVIGATION PARA QUE SEAN ESCUCHADOS

        //extrac values from intent
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movie_id", -1);
        //call the fetch method of the asyntask
        MovieDao movieDao = db.movieDao();


        //navigation

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);



        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() ==  R.id.testMenu) {
                startActivity(new Intent(getApplicationContext(), UserCreatedMoviesActivity.class));
            } else if (item.getItemId() == R.id.secondMenu) {
                startActivity(new Intent(getApplicationContext(), AllMovies.class));
            } else if (item.getItemId() == R.id.allMovies) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
            return false;
        });




        //volver a leer lo que es runOnUiThread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FetchSingleMoviesTask fetchSingleMoviesTask = new FetchSingleMoviesTask(movieDao);
                fetchSingleMoviesTask.execute(Integer.valueOf(movieId));

            }
        });

        //listener para las checkboxes
        deleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show pop up and delete this shit innit
                AlertDialog.Builder builder = new AlertDialog.Builder(SingleMovieActivity.this);
                builder.setMessage("Are you sure you want to delete it?");

                builder.setTitle("Delete movie");

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DeleteMovieTask deleteMovieTask = new DeleteMovieTask(movieDao);
                            deleteMovieTask.execute(Integer.valueOf(movieId));
                            //after deletion move to allMovies activity

                            startActivity(new Intent(SingleMovieActivity.this,AllMovies.class));
                        }
                    });

                });
                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                    //change the value of the checked property
                    deleteMovie.setChecked(false);
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });

        //update listener
        updateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass the data of such movie to new Activity
                Intent intent = new Intent(SingleMovieActivity.this, UpdateMovieActivity.class);
                //pass some data to activity

                intent.putExtra("movie_id", movieId);

                // Iniciar la actividad con el Intent configurado
                startActivity(intent);
            }
        });


    }

    //class in order to fetch single movie from DAO

    private class FetchSingleMoviesTask extends AsyncTask<Integer, Void, Movie> {
        private MovieDao movieDao;

        FetchSingleMoviesTask(MovieDao movieDao){this.movieDao = movieDao;}

        @Override
        protected Movie doInBackground(Integer... ids) {
            if(ids.length > 0) {
                int id = ids[0];
                return movieDao.singleMovie(id);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            //if (movie) {
                // Renderizar los valores en tus TextView
                singlemovieName.setText(movie.getName());
                singleMovieRating.setText(String.valueOf(movie.getRating()));
                singleMovieDescrip.setText(movie.getDescription());
                // Ajustar el rating en la RatingBar
                float ratingSingleMovie = Float.parseFloat(movie.getRating());
                singleMovieRatingBar.setRating(ratingSingleMovie);
                //Toast.makeText(SingleMovieActivity.this, " movie found", Toast.LENGTH_LONG).show();

            //user whos logged in
            int userUid = SaveSharedPreference.getUserUid(SingleMovieActivity.this);

            if(userUid == movie.getUserCreatorId()) {
                // El usuario actual es el creador de la película
                // Mostrar las opciones de editar y eliminar
                deleteMovie.setVisibility(View.VISIBLE);
                updateMovie.setVisibility(View.VISIBLE);
            } else {
                // El usuario actual no es el creador de la película
                // Ocultar las opciones de editar y eliminar
                deleteMovie.setVisibility(View.GONE);
                updateMovie.setVisibility(View.GONE);
            }

            //}
//            else {
//                // Manejar el caso donde la película no fue encontrada
//                // Por ejemplo, mostrar un mensaje de error o hacer alguna acción alternativa
//                Toast.makeText(SingleMovieActivity.this, "No movie found", Toast.LENGTH_LONG).show();
//
//            }
        }

    }

    //delete method in asyncTask
    private class DeleteMovieTask extends AsyncTask<Integer, Void, Void> {
        private MovieDao movieDao;

        DeleteMovieTask(MovieDao movieDao){this.movieDao = movieDao;}

        @Override
        protected Void  doInBackground(Integer... ids) {
            if(ids.length > 0) {
                int id = ids[0];
                Movie movieToDelete = movieDao.singleMovie(id);
                movieDao.delete(movieToDelete);
            }
            return null;

        }

    }



}


