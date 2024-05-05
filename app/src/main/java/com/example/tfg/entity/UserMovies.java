package com.example.tfg.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserMovies {
    @Embedded public User user;

    @Relation(
            parentColumn = "uid",
            entityColumn = "userCreatorId"
    )

    public List<Movie> movies;
}


