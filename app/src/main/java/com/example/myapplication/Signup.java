package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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

public class Signup extends AppCompatActivity {

    EditText etSignupPassword, etSignupUserName;
    Button btnSignup;
    TextView signin_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    private void init (){
        btnSignup = findViewById(R.id.btnSignup);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etSignupUserName = findViewById(R.id.etSignupUserName);
        signin_link = findViewById(R.id.signin_link);
        signin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler database = new DatabaseHandler(Signup.this);
                database.open();
                database.signupUser(etSignupUserName.getText().toString().trim(), etSignupPassword.getText().toString().trim());
                database.close();
                etSignupPassword.setText("");
                etSignupUserName.setText("");
            }
        });
    }
}