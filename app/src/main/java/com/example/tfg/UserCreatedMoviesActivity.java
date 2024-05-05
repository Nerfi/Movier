package com.example.tfg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.dao.MovieDao;
import com.example.tfg.dao.UserDao;
import com.example.tfg.entity.User;
import com.example.tfg.entity.Movie;
import com.example.tfg.entity.UserMovies;

import java.util.List;

public class UserCreatedMoviesActivity extends AppCompatActivity {
    private UserDao userDao;
    private MovieDao movieDao;
    private ListView moviesListView;

    AppDatabase db;

    private int userUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_created_movies);
        db = AppDatabase.getDatabase(this);


        moviesListView = findViewById(R.id.moviesListView);


        if(!SaveSharedPreference.getUserName(UserCreatedMoviesActivity.this).isEmpty()) {
            userUid = SaveSharedPreference.getUserUid(UserCreatedMoviesActivity.this);

        }



    new GetMoviesTask().execute(userUid);

    }
    private class GetMoviesTask extends AsyncTask<Integer, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(Integer... userIds) {
            // Obtén el Dao
            userDao = db.userDao();
            movieDao = db.movieDao();

            // Obtén el usuario y sus películas
            List<UserMovies> userMovies = userDao.getUsersWithMovies();
            for (UserMovies um : userMovies) {
                if (um.user.getUserUid() == userIds[0]) {
                    return um.movies;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            // Ahora puedes usar la lista de películas
            MovieAdapter movieAdapter = new MovieAdapter(UserCreatedMoviesActivity.this, R.layout.movie_item, movies);
            moviesListView.setAdapter(movieAdapter);

        }


    }


}
