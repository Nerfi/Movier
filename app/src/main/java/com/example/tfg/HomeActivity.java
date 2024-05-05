package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class HomeActivity extends AppCompatActivity {

    private RatingBar ratingMovie;

    private int userUid;

    //get reference to db
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        //initialize
        db = AppDatabase.getDatabase(this);



        //initialice/ find the elements in the  UI
        Button createMovieBtn = findViewById(R.id.createMovieBtn);
        EditText movieName = findViewById(R.id.movieName);
        EditText movieDescription = findViewById(R.id.movieDescription);
        EditText movieReview = findViewById(R.id.movieReview);
        ratingMovie = findViewById(R.id.ratingBar);
        ratingMovie.setNumStars(5);


        //getting the navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.testMenu) {
                startActivity(new Intent(getApplicationContext(), AllMovies.class));
            } else if (item.getItemId() == R.id.secondMenu) {
                //cambiar esto para que vay a mis movies creadas por el usuario
                startActivity(new Intent(getApplicationContext(), AllMovies.class));
            } else if (item.getItemId() == R.id.allMovies) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }

            return false;
        });


        //rest of the logic

        if(!SaveSharedPreference.getUserName(HomeActivity.this).isEmpty()) {
            userUid = SaveSharedPreference.getUserUid(HomeActivity.this);

        }


        //listening to rating change event when user press

        createMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dada la naturaleza de android para obtener los valores escritos por el usuario debemos hacerlo aqui, en el handler
                //ya que si lo hacemos en onCreate no le habremos dado la oportunidad al usuario de haber escrito nada
                //para cuando llegemos a onCreate
                String rating = String.valueOf(ratingMovie.getRating());
                //get the values of the elements in the UI
                String nameOfMovie = movieName.getText().toString();
                String descrip = movieDescription.getText().toString();
                String review = movieReview.getText().toString();
                String ratingValue = String.valueOf(ratingMovie.getRating());

                //check for empty values onSubmit
                if(nameOfMovie.isEmpty() || descrip.isEmpty() || ratingValue.isEmpty() || review.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Values need to be written", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //if values are not empty submit to DB

                    Movie newMovie = new Movie(nameOfMovie, rating, descrip, review);
                    newMovie.userCreatorId = userUid; // Asegúrate de tener una referencia al usuario actualmente conectado
                    //save into DB
                    MovieDao movieDao = db.movieDao();
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                movieDao.insertMovie(newMovie);
//                                Toast.makeText(getApplicationContext(), "Movie created", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(HomeActivity.this, AllMovies.class));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Movie created", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(HomeActivity.this, AllMovies.class));
                                    }
                                });

                            }
                        }).start();
                    }catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error creating movie!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
                



            }
        });


    }
}