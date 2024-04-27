package com.example.myapplication;

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

public class EditPassword extends AppCompatActivity {
    EditText etEditAppName, etEditAppUserName, etEditAppPassword;
    Button btnSaveChanges;
    int appId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        Intent intent = getIntent();

        appId = intent.getIntExtra("appId", -1);
        String appUsername = intent.getStringExtra("appUsername");
        String appPassword = intent.getStringExtra("appPassword");
        String appName = intent.getStringExtra("appName");

        etEditAppName.setText(appName);
        etEditAppUserName.setText(appUsername);
        etEditAppPassword.setText(appPassword);
    }

    private void init () {
        etEditAppName = findViewById(R.id.etEditAppName);
        etEditAppUserName = findViewById(R.id.etEditAppUserName);
        etEditAppPassword = findViewById(R.id.etEditAppPassword);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler databaseHandler = new DatabaseHandler(EditPassword.this);
                databaseHandler.open();
                databaseHandler.editPassword(appId, etEditAppName.getText().toString().trim(), etEditAppUserName.getText().toString().trim(),etEditAppPassword.getText().toString().trim());
                databaseHandler.close();

                etEditAppName.setText("");
                etEditAppUserName.setText("");
                etEditAppPassword.setText("");

                Intent intent = new Intent(EditPassword.this, Home.class);
                startActivity(intent);
            }
        });
    }
}