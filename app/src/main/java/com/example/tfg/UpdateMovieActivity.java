package com.example.tfg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.dao.MovieDao;
import com.example.tfg.entity.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UpdateMovieActivity extends AppCompatActivity {

    private EditText movieName, movieDescription, movieReview;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_movie);
        db = AppDatabase.getDatabase(this);

        //extracting the values form intente comming from SingleMovieActivity
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movie_id", -1);

        //navbar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //add click listeners for the navigation

        //update movie btn
        Button updateMoviebtn = findViewById(R.id.updateMovieBtn);

        //init the UI
        movieName = findViewById(R.id.editMovieName);
        movieDescription = findViewById(R.id.editMovieDescription);
        movieReview = findViewById(R.id.editMovieReview);

        updateMoviebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the new values

                String newMovieName = movieName.getText().toString();
                String newMovieDescription = movieDescription.getText().toString();
                String newMovieReview = movieReview.getText().toString();
                //UPDATE MOVIE

                updateMovie(movieId,newMovieName,newMovieDescription, newMovieReview);

                // Mostrar el Toast y moverse de regreso a la actividad de todas las películas en el hilo principal de la interfaz de usuario
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Movie Updated", Toast.LENGTH_LONG).show();
                        // Mover de regreso a la actividad de todas las películas
                        startActivity(new Intent(UpdateMovieActivity.this, AllMovies.class));
                    }
                });






            }
        });

        MovieDao movieDao = db.movieDao();

        new Thread(new Runnable() {
            @Override
            public void run() {

                FetchSingleMoviesTask fethSingleMovie = new FetchSingleMoviesTask(movieDao);
                fethSingleMovie.execute(Integer.valueOf(movieId));
            }
        }).start();







    }

    private void updateMovie(int id , String name, String description, String review){
        Movie updatedMovie = new Movie(name,"", description, review);
        // Obtener el DAO de la base de datos
        MovieDao movieDao = db.movieDao();
        updatedMovie.setUid(id); // Asegúrate de establecer el ID correcto

        new Thread(new Runnable() {
            @Override
            public void run() {
                movieDao.updateMovie(updatedMovie);
            }
        }).start();


    }



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

            movieName.setText(movie.getName());
            movieDescription.setText(movie.getDescription());
            movieReview.setText(movie.getReview());

        }

    }

}