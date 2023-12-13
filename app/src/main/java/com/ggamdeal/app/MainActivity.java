package com.ggamdeal.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ggamdeal.app.login.LoginActivity;
import com.ggamdeal.app.welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        //최초실행했는지 파악하기 위한 코드
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isInit = sharedPreferences.getBoolean("isInit", false);
        if (!isInit) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isInit", true);
            editor.apply();

            new Handler().postDelayed(() -> {
                Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
            }, 500); //최초실행시에는, WelcomeActivity 시작
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }
}