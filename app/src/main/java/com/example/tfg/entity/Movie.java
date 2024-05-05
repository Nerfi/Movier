package com.example.tfg.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie {
    //en el tutorial nos la primary key asi: @PrimaryKey public long userId;, ver si no nos da problemas despues
    @PrimaryKey(autoGenerate = true)
    public int uid;

    //foreging key
    public long userCreatorId;

    @ColumnInfo(name = "movie_name")
    public String movie_name;

    @ColumnInfo(name = "rating")
    public String rating;

    @ColumnInfo(name = "description")
    public String description;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(long userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getName() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @ColumnInfo(name = "review")
    public String review;

    public Movie(String movie_name, String rating, String description, String review) {
        this.movie_name = movie_name;
        this.rating = rating;
        this.description = description;
        this.review = review;
    }


}
