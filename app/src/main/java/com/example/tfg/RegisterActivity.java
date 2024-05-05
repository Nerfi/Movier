package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.dao.UserDao;
import com.example.tfg.entity.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUsername, registerEmail, registerPass;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        db = AppDatabase.getDatabase(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //find UI elements

        registerUsername = findViewById(R.id.registerUsername);
        registerEmail = findViewById(R.id.editTextTextEmailAddress);
        registerPass = findViewById(R.id.editTextTextPassword);
        Button registerBtn = findViewById(R.id.regBtn);

        ///set click listener

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the values from the EditText elements
                String regUserName = registerUsername.getText().toString().trim();
                String regEmail = registerEmail.getText().toString().trim();
                String regPass = registerPass.getText().toString().trim();

                // Validar los datos
                if (regUserName.isEmpty() || regEmail.isEmpty() || regPass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor completa todos los campos", Toast.LENGTH_LONG).show();
                    return;
                }




                // crear una nueva instancia de User
                User newUser = new User(regUserName,regEmail, regPass);

                //obtener instancia de la base de datos
              //  AppDatabase db = AppDatabase.getDatabase(this);
                UserDao userDao = db.userDao();

                //insertar el nuevo usuario en la base de datos
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            userDao.registerUser(newUser);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }
                }).start();




            }
        });


    }
}