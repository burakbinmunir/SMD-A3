package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewPassword extends AppCompatActivity {

    EditText etAppName, etAppUserName, etAppPassword;
    Button btnSave;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }
    private void init () {
        context = this;
        etAppName = findViewById(R.id.etAppName);
        etAppUserName = findViewById(R.id.etAppUserName);
        etAppPassword = findViewById(R.id.etAppPassword);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(NewPassword.this);
                databaseHandler.open();
                SharedPreferences sharedPreferences = context.getSharedPreferences("LoginCredPrefs", Context.MODE_PRIVATE);
                String loggedInUsername = sharedPreferences.getString("loggedInUsername", "");

                databaseHandler.addNewPassword(loggedInUsername, etAppName.getText().toString().trim(),
                        etAppUserName.getText().toString().trim(),
                        etAppPassword.getText().toString().trim());

                etAppName.setText("");
                etAppPassword.setText("");
                etAppUserName.setText("");

                Intent intent = new Intent(NewPassword.this, Home.class);
                startActivity(intent);

                databaseHandler.close();
            }
        });
    }
}