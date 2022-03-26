package com.example.benefit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* TODO: Delete this button/functions - only testing logout feature */
        Button logoutButton = findViewById(R.id.test_logout_button);
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            com.facebook.login.LoginManager.getInstance().logOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

    }
}