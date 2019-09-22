package com.zona.recreativacr;

import androidx.appcompat.app.AppCompatActivity;
import database.DBConnection;
import database.RequestLogin;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    private EditText usernameET;
    private EditText passwordET;
    private Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameET = findViewById(R.id.usernameText);
        passwordET = findViewById(R.id.passwordText);


        con = DBConnection.getInstance();

    }

    public void login(View v){
        RequestLogin rl = new RequestLogin(con);

        int result = rl.loginAttempt(usernameET.getText().toString(), passwordET.getText().toString());

        if(result == 0){
            Toast.makeText(getApplicationContext(),
                    "EXITO",
                    Toast.LENGTH_LONG).show();
        }else if(result == -1){
            Toast.makeText(getApplicationContext(),
                    "Credenciales invalidos",
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),
                    "ERROR EN LA CONEXION DE LA BASE DE DATOS",
                    Toast.LENGTH_LONG).show();
        }
    }
}
