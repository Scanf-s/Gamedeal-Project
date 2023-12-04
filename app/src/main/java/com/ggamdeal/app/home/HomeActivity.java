package com.ggamdeal.app.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import com.ggamdeal.app.R;

import me.relex.circleindicator.CircleIndicator3;

import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {

    private ViewPager2 homeTopViewPager;
    private homeTopElementPagerAdapter adapter;
    CircleIndicator3 indicator;
    private static final String TAG = "EmailPassword";
    ImageView communityImgButton;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // ViewPager 설정
        homeTopViewPager = findViewById(R.id.homeTopViewPager);
        adapter = new homeTopElementPagerAdapter(this);
        homeTopViewPager.setAdapter(adapter);

        // CircleIndicator 설정
        indicator = findViewById(R.id.homeTopElementPageIndicator);
        indicator.setViewPager(homeTopViewPager);

        communityImgButton = findViewById(R.id.communityButton);
        communityImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                finish();
            }
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.item_info) {
                Log.d("DrawerINFO", "Select item info");
            } else if (id == R.id.item_notice) {
                Log.d("DrawerINFO", "Select item notice");
            } else if (id == R.id.item_report) {
                Log.d("DrawerINFO", "Select item report");
            } else if (id == R.id.item_setting) {
                Log.d("DrawerINFO", "Select item setting");
            } else if (id == R.id.item_service_center) {
                Log.d("DrawerINFO", "Select item service center");
            }
            return false;
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("USERINFO", "현재 로그인 된 유저의 정보를 가져옵니다.");
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            View headerView = navigationView.getHeaderView(0);
            TextView userName = headerView.findViewById(R.id.username);
            userName.setText(userEmail);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
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