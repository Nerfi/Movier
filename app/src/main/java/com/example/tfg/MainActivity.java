package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.tfg.dao.UserDao;
import com.example.tfg.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity  {
Button loginBtn;
Button registerBtn;


    //ids of navigation items
    //PD:CAMBIAR ESTOS NOMBRES
    final int HOME = R.id.testMenu;
    final int AGENCE = R.id.secondMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);



        //navigation

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


        //juan´s  code to navigate
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this, LoginActivity.class);

              startActivity(intent);


           }
       });
        //register action
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });



    }


}