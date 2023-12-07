package com.ggamdeal.app.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import com.ggamdeal.app.R;

import me.relex.circleindicator.CircleIndicator3;

import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton fabScrollToTop;
    private Handler handler = new Handler();
    private final int DELAY_MS = 3000; // 3초
    private final int PERIOD_MS = 3000; // 3초
    private static final String TAG = "EmailPassword";
    private ViewPager2 homeTopViewPager;
    private RecyclerView actionRecyclerView;
    private RecyclerView casualRecyclerView;
    private RecyclerView horrorRecyclerView;
    private TopElementAdapter topAdapter;
    private ActionElementAdapter actionAdapter;
    private CasualElementAdpater casualAdapter;
    private HorrorElementAdapter horrorAdapter;
    TextView showMore1;
    TextView showMore2;
    TextView showMore3;
    CircleIndicator3 indicator;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Log.d("AutoSlide", "AutoSlide 실행됨");
        startAutoSlide();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ViewPager 설정
        homeTopViewPager = findViewById(R.id.homeTopViewPager);
        topAdapter = new TopElementAdapter(this);
        homeTopViewPager.setAdapter(topAdapter);

        actionRecyclerView = findViewById(R.id.homeMiddleRecyclerView);
        actionAdapter = new ActionElementAdapter(this);
        actionRecyclerView.setAdapter(actionAdapter);

        casualRecyclerView = findViewById(R.id.homeMiddleRecyclerView2);
        casualAdapter = new CasualElementAdpater(this);
        casualRecyclerView.setAdapter(casualAdapter);

        horrorRecyclerView = findViewById(R.id.homeMiddleRecyclerView3);
        horrorAdapter = new HorrorElementAdapter(this);
        horrorRecyclerView.setAdapter(horrorAdapter);

        // CircleIndicator 설정
        indicator = findViewById(R.id.homeTopElementPageIndicator);
        indicator.setViewPager(homeTopViewPager);

        drawerLayout = findViewById(R.id.drawerLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.item_myinfo) {
                Log.d("DrawerINFO", "내 정보 버튼 클릭");
            } else if (id == R.id.item_community) {
                Log.d("DrawerINFO", "커뮤니티 버튼 클릭");
            } else if (id == R.id.item_wishlist) {
                Log.d("DrawerINFO", "위시리스트 버튼 클릭");
            } else if (id == R.id.item_logout) {
                Log.d("DrawerINFO", "로그아웃");
                signOut();
                finish();
            }
            return false;
        });

        //더보기 버튼 설정
        showMore1 = findViewById(R.id.homeMiddleTextTitleSecondary);
        showMore1.setOnClickListener(v -> {
            if(actionRecyclerView.getVisibility() == View.GONE) {
                actionRecyclerView.setAlpha(0f);
                actionRecyclerView.setVisibility(View.VISIBLE);
                actionRecyclerView.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null);
                showMore1.setText("닫기");
            }
            else if((actionRecyclerView.getVisibility() == View.VISIBLE) && showMore1.getText().equals("닫기")){
                actionRecyclerView.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                actionRecyclerView.setVisibility(View.GONE); // INVISIBLE 대신 GONE으로 변경
                            }
                        });
                showMore1.setText("더보기");
            }
        });

        //더보기 버튼 설정
        showMore2 = findViewById(R.id.homeMiddleTextTitleSecondary2);
        showMore2.setOnClickListener(v -> {
            if (casualRecyclerView.getVisibility() == View.GONE) {
                casualRecyclerView.setAlpha(0f);
                casualRecyclerView.setVisibility(View.VISIBLE);
                casualRecyclerView.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null);
                showMore2.setText("닫기");

                //Indie 태그를 열었다면, Action 태그를 닫아야함
                actionRecyclerView.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                actionRecyclerView.setVisibility(View.GONE); // INVISIBLE 대신 GONE으로 변경
                            }
                        });
                showMore1.setText("더보기");
            } else if ((casualRecyclerView.getVisibility() == View.VISIBLE) && showMore2.getText().equals("닫기")) {
                casualRecyclerView.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                casualRecyclerView.setVisibility(View.GONE); // INVISIBLE 대신 GONE으로 변경
                            }
                        });
                showMore2.setText("더보기");
            }
        });

        //더보기 버튼 설정
        showMore3 = findViewById(R.id.homeMiddleTextTitleSecondary3);
        showMore3.setOnClickListener(v -> {
            if (horrorRecyclerView.getVisibility() == View.GONE) {
                horrorRecyclerView.setAlpha(0f);
                horrorRecyclerView.setVisibility(View.VISIBLE);
                horrorRecyclerView.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null);
                showMore3.setText("닫기");

                // Hide homeMiddleRecyclerView when homeMiddleRecyclerView2 is expanded
                actionRecyclerView.setVisibility(View.GONE);
                casualRecyclerView.setVisibility(View.GONE);
                showMore1.setText("더보기");
                showMore2.setText("더보기");
            } else if ((horrorRecyclerView.getVisibility() == View.VISIBLE) && showMore3.getText().equals("닫기")) {
                horrorRecyclerView.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                horrorRecyclerView.setVisibility(View.GONE); // INVISIBLE 대신 GONE으로 변경
                            }
                        });
                showMore3.setText("더보기");
            }
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
    protected void onDestroy() {
        super.onDestroy();
        stopAutoSlide(); // 자동 슬라이딩 정리
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

    private Runnable runnable = new Runnable() {
        public void run() {
            if (topAdapter.getItemCount() > 0) {
                int currentPosition = homeTopViewPager.getCurrentItem();
                int nextPosition = (currentPosition + 1) % topAdapter.getItemCount();
                homeTopViewPager.setCurrentItem(nextPosition);
            }
            handler.postDelayed(this, PERIOD_MS);
        }
    };

    private void startAutoSlide() {
        handler.postDelayed(runnable, DELAY_MS);
    }

    private void stopAutoSlide() {
        handler.removeCallbacks(runnable);
    }
}