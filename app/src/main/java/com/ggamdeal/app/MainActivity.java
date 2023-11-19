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

        //최초실행했는지 파악하기 위한 코드
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isInit = sharedPreferences.getBoolean("isInit", false);

        if (!isInit) {
            //어플리케이션이 최초로 실행되는지 확인하는 flag에 따라 WelcomePage를 보여줌
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isInit", true);
            editor.apply();

            // WelcomeActivity 실행, LoginActivity 종료 (현재는 MainActivity가 LoginActivity)
            Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
            startActivity(welcomeIntent);
            finish();
        } else {
            //최초실행 아니면 loginpage로 넘어감
            setContentView(R.layout.loginpage);
        }

        //Welcomepage로 돌아가기 위해 비밀번호 변경 버튼을 따로 설정해줌 (이후 삭제 예정)
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