package com.ggamdeal.app.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggamdeal.app.MainActivity;
import com.ggamdeal.app.R;
import com.ggamdeal.app.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private WelcomePagerAdapter adapter;
    private ImageView nextImageButton;
    private int currentPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);

        viewPager = findViewById(R.id.viewPager);
        nextImageButton = findViewById(R.id.nextImageButton);

        List<String> pageTexts = new ArrayList<>();
        pageTexts.add(getString(R.string.welcome_string_1));
        pageTexts.add(getString(R.string.welcome_string_2));
        pageTexts.add(getString(R.string.welcome_string_3));

        adapter = new WelcomePagerAdapter(pageTexts);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;

                // Update visibility of nextImageButton
                if (currentPage < pageTexts.size() - 1) {
                    nextImageButton.setVisibility(View.INVISIBLE);
                } else {
                    nextImageButton.setVisibility(View.VISIBLE);
                }
            }
        });

        nextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() < pageTexts.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    navigateToNextActivity();
                }
            }
        });
    }

    private void navigateToNextActivity() {
        // Start the MainActivity
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish(); // Optional: Finish the WelcomeActivity so the user can't go back to it.
    }
}