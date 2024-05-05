package com.example.tfg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.dao.MovieDao;
import com.example.tfg.entity.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
//



public class AllMovies extends AppCompatActivity {

    private ListView moviesListView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_movies);
        db = AppDatabase.getDatabase(this);

        moviesListView = findViewById(R.id.moviesListView);
        //getting the navigation
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

        MovieDao movieDao = db.movieDao();
        new FetchMoviesTask(movieDao).execute();

    }

    private class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {
        private MovieDao movieDao;

        FetchMoviesTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return movieDao.getAllMovies();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            MovieAdapter movieAdapter = new MovieAdapter(AllMovies.this, R.layout.movie_item, movies);

            //adding listener de prueba




            moviesListView.setAdapter(movieAdapter);
        }
    }
}
