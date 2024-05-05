package com.example.tfg.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.tfg.entity.User;
import com.example.tfg.entity.UserMovies;

import java.util.List;
//example sqlite , delete if necesary

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);


    @Query("SELECT * FROM user WHERE user_name = :userName AND password = :password")
    User loginUser(String userName, String password);

    @Insert
    void insertAll(User... users);

    @Insert
    void  registerUser(User user);

    @Delete
    void delete(User user);

    @Transaction
    @Query("SELECT * FROM User")
    List<UserMovies> getUsersWithMovies();
}

