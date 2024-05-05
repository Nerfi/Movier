package com.example.tfg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.dao.UserDao;
import com.example.tfg.entity.User;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

   private EditText username, password;
    ConstraintLayout layout = null;

    //User userloggedIn;

    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        db = AppDatabase.getDatabase(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //juans code in order to get the values from the login email and passoword
        // initialize UI elements

        username = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextPassword);
        Button loginBtn = findViewById(R.id.button);

        //setting a click listener for the login button

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // retrieve user name and password
                String usernameCredential = username.getText().toString().trim();
                String passwordCredential = password.getText().toString().trim();

                //check if campos are empty
                if(usernameCredential.isEmpty() || passwordCredential.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor completa todos los campos", Toast.LENGTH_LONG).show();
                    return;
                }

                //obtener instancia de la base de datos
//                AppDatabase db = MainActivity.getDatabase();
                UserDao userDao = db.userDao();

               // userloggedIn = userDao.loginUser(usernameCredential,passwordCredential);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //new one
                            User userloggedIn = userDao.loginUser(usernameCredential,passwordCredential);
                            //context

                            if(userloggedIn != null) {

                                //saved userName into sharedPreferences
                                SaveSharedPreference.setUserName(LoginActivity.this,usernameCredential);
                                //sve uid user into sharedPreferences
                                SaveSharedPreference.setUserUid(LoginActivity.this,userloggedIn.getUserUid());

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "LOGGED IN BRODHA", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this, AllMovies.class);
                                     startActivity(intent);

                                    }
                                });

                                return;
                            } else {
                                // Si no se encontró al usuario, mostrar un mensaje de error en el hilo principal
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }


                            return;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });




    };



}