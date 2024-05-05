package com.example.tfg.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//sqlite room example, delete this in case of error

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    public  User(String userName, String email, String password ) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    //return the uid
    public int getUserUid() {
        return uid;
    }
}