package com.ggamdeal.app.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggamdeal.app.MainActivity;
import com.ggamdeal.app.R;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView nextImageButton;
    private TextView welcomePageTextContents;
    private int currentPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);

        nextImageButton = findViewById(R.id.nextImageButton);
        welcomePageTextContents = findViewById(R.id.welcomepagetextcontents);

        nextImageButton.setOnClickListener(view -> {
            //클릭하면 다음 text를 보여주어야 함
            changeTextForNextPage();
        });
    }
    private void changeTextForNextPage() {
        String[] PAGE_TEXTS = {
                "",
                (String) getResources().getText(R.string.welcome_string_2),
                (String) getResources().getText(R.string.welcome_string_3)
        };

        // 페이지 개수 만큼 수행
        if (currentPage < PAGE_TEXTS.length) {
            //현재 페이지의 TextView의 text를 변경해줌
            welcomePageTextContents.setText(PAGE_TEXTS[currentPage]);

            //페이지 증가
            currentPage++;
        } else {
            // 더이상 welcomepage가 없을 경우, 다음 Activity 실행
            navigateToNextActivity();
        }
    }

    private void navigateToNextActivity() {
        //loginActivty 실행
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }
}