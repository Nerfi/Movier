package com.example.tfg.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tfg.entity.Movie;
import com.example.tfg.entity.User;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movie WHERE uid = :id LIMIT 1")
    Movie singleMovie(int id);

    @Insert
    long insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void delete(Movie movie);
}
