package com.example.tfg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tfg.entity.Movie;

import java.util.List;
public class MovieAdapter extends ArrayAdapter<Movie> {
    //for the listener

    //stack overflow for the customAdapter ListView problem https://stackoverflow.com/questions/11265743/onitemclicklistener-with-custom-adapter-and-listview
    private Context context;
    public MovieAdapter(Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);

        }

        TextView movieName = convertView.findViewById(R.id.movie_name);
        TextView movieDescription = convertView.findViewById(R.id.movie_description);
        TextView movieReview = convertView.findViewById(R.id.movie_review);
        RatingBar movieRating = convertView.findViewById(R.id.ratingBar);
        movieName.setText(movie.movie_name);

        // Set an OnClickListener for the convertView

    convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SingleMovieActivity.class);
            //pass some data to activity
            intent.putExtra("movie_name", movie.movie_name );
            intent.putExtra("movie_id", movie.uid);

            //add other movies details as needed
            context.startActivity(intent);
        }
    });


//        float rating = Float.parseFloat(movie.rating);
//
//
//        movieRating.setRating(rating);
//pequeña solucion por el error en update movie
        float rating;
        if (movie.rating != null && !movie.rating.isEmpty()) {
            rating = Float.parseFloat(movie.rating);
        } else {
            // Si movie.rating es nulo o vacío, establece rating en 0
            rating = 0;
        }
        movieRating.setRating(rating);

        movieDescription.setText(movie.description);
        movieReview.setText(movie.review);


        return convertView;
    }
}