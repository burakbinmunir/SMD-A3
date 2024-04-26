package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    RecyclerView rvPasswords;
    PasswordAdapter passwordAdapter;
    ArrayList<Password> passwordArrayList;

    FloatingActionButton fabNewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    private void init () {
        fabNewPassword = findViewById(R.id.fabNewPassword);

        fabNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, NewPassword.class);
                startActivity(intent);
            }
        });

        passwordArrayList = new ArrayList<>();
        passwordArrayList.add(new Password("insta", "OmerFarok", "OMerFarok"));
        passwordArrayList.add(new Password("insta", "OmerFarok", "OMerFarok"));
        passwordArrayList.add(new Password("insta", "OmerFarok", "OMerFarok"));
        passwordArrayList.add(new Password("insta", "OmerFarok", "OMerFarok"));
        passwordArrayList.add(new Password("insta", "OmerFarok", "OMerFarok"));
        passwordArrayList.add(new Password("insta", "OmerFarok", "OMerFarok"));

        rvPasswords = findViewById(R.id.rvPasswords);
        rvPasswords.setHasFixedSize(true);
        rvPasswords.setLayoutManager(new LinearLayoutManager(this));
        passwordAdapter = new PasswordAdapter(passwordArrayList);
        rvPasswords.setAdapter(passwordAdapter);

    }
}