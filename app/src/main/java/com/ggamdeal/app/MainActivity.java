package com.ggamdeal.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ggamdeal.app.welcome.WelcomeActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if it's the first run
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isInit = sharedPreferences.getBoolean("isInit", false);

        if (!isInit) {
            // Set the flag to true indicating the app has been initialized
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isInit", true);
            editor.apply();

            // Start WelcomeActivity and finish MainActivity
            Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
            startActivity(welcomeIntent);
            finish();
        } else {
            // It's not the first run, continue with the normal flow
            setContentView(R.layout.loginpage);
            // Your existing code for MainActivity goes here
        }

        TextView button = findViewById(R.id.findPasswordTextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToWelcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(returnToWelcomeIntent);
            }
        });
    }
}