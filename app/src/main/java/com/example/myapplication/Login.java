package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText etLoginPassword, etLoginUserName;
    TextView signup_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    private void init (){
        btnLogin = findViewById(R.id.btnLogin);
        signup_link = findViewById(R.id.signup_link);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        etLoginUserName = findViewById(R.id.etLoginUserName);

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler database = new DatabaseHandler(Login.this);
                database.open();
                if (database.verifyUser(etLoginUserName.getText().toString().trim(), etLoginPassword.getText().toString().trim())) {
                    database.close();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(Login.this, "Wrong credentiale, please try again..", Toast.LENGTH_LONG).show();
                    database.close();
                }
            }
        });
    }
}