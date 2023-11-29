package com.ggamdeal.app.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import com.ggamdeal.app.R;

import me.relex.circleindicator.CircleIndicator3;


public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImagePagerAdapter adapter;
    CircleIndicator3 indicator;
    private static final String TAG = "EmailPassword";
    ImageView communityImgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // ViewPager 설정
        viewPager = findViewById(R.id.homeTopImgViewPager);
        adapter = new ImagePagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Instantiate the CircleIndicator
        indicator = findViewById(R.id.homeTopImgPageIndicator);
        indicator.setViewPager(viewPager);

        communityImgButton = findViewById(R.id.communityButton);
        communityImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                finish();
            }
        });
    }

    private void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        // Check if there is no current user.
        if (firebaseAuth.getCurrentUser() == null)
            Log.d(TAG, "signOut:success");
        else
            Log.d(TAG, "signOut:failure");
    }
}