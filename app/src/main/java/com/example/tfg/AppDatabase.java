package com.example.tfg;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tfg.dao.MovieDao;
import com.example.tfg.dao.UserDao;
import com.example.tfg.entity.Movie;
import com.example.tfg.entity.User;

//@Database(entities = {User.class, Movie.class}
//la línea de arriba declara todas las tablas que tiene nuestra bbdd
@Database(entities = {User.class, Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract MovieDao movieDao();
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "TFGDB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
