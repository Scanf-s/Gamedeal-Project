package com.ggamdeal.app.welcome;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.ggamdeal.app.R;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private WelcomePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewPager = findViewById(R.id.viewPager);
        adapter = new WelcomePagerAdapter();
        viewPager.setAdapter(adapter);
    }
}