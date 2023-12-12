package com.ggamdeal.app.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ggamdeal.app.R;
import com.ggamdeal.app.community.CommunityFragment;
import com.ggamdeal.app.login.LoginActivity;
import com.ggamdeal.app.news.NewsFragment;
import com.ggamdeal.app.wishlist.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navigationBarView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FirebaseUser currentUser;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        navigationBarView = findViewById(R.id.bottom_navigation);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        toolbar = findViewById(R.id.toolbar);

        transferTo(HomeFragment.newInstance("param1", "param2"));

        navigationBarView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item_home) {
                transferTo(HomeFragment.newInstance("param1", "param2"));
                return true;
            }

            if (itemId == R.id.item_community) {
                transferTo(CommunityFragment.newInstance("param1", "param2"));
                return true;
            }

            if (itemId == R.id.item_wishlist) {
                transferTo(WishlistFragment.newInstance("param1", "param2"));
                return true;
            }

            if (itemId == R.id.item_news) {
                transferTo(NewsFragment.newInstance("param1", "param2"));
                return true;
            }
            return false;
        });

        navigationBarView.setOnItemReselectedListener(item -> {
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.item_myinfo) {
                Log.d("DrawerINFO", "내 정보 버튼 클릭");
            } else if (id == R.id.item_logout) {
                Log.d("DrawerINFO", "로그아웃");
                signOut();
            }
            return false;
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            View headerView = navigationView.getHeaderView(0);
            TextView userName = headerView.findViewById(R.id.username);
            userName.setText(userEmail);
        }
    }

    private void transferTo(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        if (firebaseAuth.getCurrentUser() == null) {
            Log.d("Firebase", "signOut:success");
            finish();
        }
        else Log.d("Firebase", "signOut:failure");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}