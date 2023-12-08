package com.ggamdeal.app.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import com.ggamdeal.app.home.adapter.ActionElementAdapter;
import com.ggamdeal.app.home.adapter.CasualElementAdpater;
import com.ggamdeal.app.home.adapter.HorrorElementAdapter;
import com.ggamdeal.app.home.adapter.IndieElementAdapter;
import com.ggamdeal.app.home.adapter.TopElementAdapter;
import com.ggamdeal.app.wishlist.WishlistFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ggamdeal.app.R;

import me.relex.circleindicator.CircleIndicator3;

import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private final int DELAY_MS = 3000; // 3초
    private final int PERIOD_MS = 3000; // 3초
    private static final String TAG = "EmailPassword";
    private ViewPager2 homeTopViewPager;
    private RecyclerView actionRecyclerView;
    private RecyclerView casualRecyclerView;
    private RecyclerView horrorRecyclerView;
    private RecyclerView indieRecyclerView;
    private TopElementAdapter topAdapter;
    private ActionElementAdapter actionAdapter;
    private CasualElementAdpater casualAdapter;
    private HorrorElementAdapter horrorAdapter;
    private IndieElementAdapter indieAdapter;

    TextView showMore1;
    TextView showMore2;
    TextView showMore3;
    TextView showMore4;
    CircleIndicator3 indicator;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView navigationBarView;
    FirebaseUser currentUser;

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        navigationBarView = findViewById(R.id.bottom_navigation);

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

        indieRecyclerView = findViewById(R.id.homeMiddleRecyclerView4);
        indieAdapter = new IndieElementAdapter(this);
        indieRecyclerView.setAdapter(indieAdapter);

        // CircleIndicator 설정
        indicator = findViewById(R.id.homeTopElementPageIndicator);
        indicator.setViewPager(homeTopViewPager);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigationView);

        //더보기 버튼 설정
        showMore1 = findViewById(R.id.homeMiddleTextTitleSecondary);
        showMore2 = findViewById(R.id.homeMiddleTextTitleSecondary2);
        showMore3 = findViewById(R.id.homeMiddleTextTitleSecondary3);
        showMore4 = findViewById(R.id.homeMiddleTextTitleSecondary4);

        //Firebase 설정
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("Firebase", "현재 로그인 된 유저의 정보를 가져옵니다.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Log.d("AutoSlide", "AutoSlide 실행됨");
        startAutoSlide();

        //초기화 함수 실행
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.item_myinfo) {
                Log.d("DrawerINFO", "내 정보 버튼 클릭");
            } else if (id == R.id.item_logout) {
                Log.d("DrawerINFO", "로그아웃");
                signOut();
                finish();
            }
            return false;
        });

        showMore1.setOnClickListener(v -> {
            if (actionRecyclerView.getVisibility() == View.GONE) {
                actionRecyclerView.setAlpha(0f);
                actionRecyclerView.setVisibility(View.VISIBLE);
                actionRecyclerView.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null);
                showMore1.setText("닫기");
            } else if ((actionRecyclerView.getVisibility() == View.VISIBLE) && showMore1.getText().equals("닫기")) {
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

        showMore4.setOnClickListener(v -> {
            if (indieRecyclerView.getVisibility() == View.GONE) {
                indieRecyclerView.setAlpha(0f);
                indieRecyclerView.setVisibility(View.VISIBLE);
                indieRecyclerView.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null);
                showMore4.setText("닫기");

                // Hide homeMiddleRecyclerView when homeMiddleRecyclerView2 is expanded
                actionRecyclerView.setVisibility(View.GONE);
                casualRecyclerView.setVisibility(View.GONE);
                showMore1.setText("더보기");
                showMore2.setText("더보기");
                showMore3.setText("더보기");
            } else if ((indieRecyclerView.getVisibility() == View.VISIBLE) && showMore4.getText().equals("닫기")) {
                indieRecyclerView.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                indieRecyclerView.setVisibility(View.GONE); // INVISIBLE 대신 GONE으로 변경
                            }
                        });
                showMore4.setText("더보기");
            }
        });

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            View headerView = navigationView.getHeaderView(0);
            TextView userName = headerView.findViewById(R.id.username);
            userName.setText(userEmail);
        }

        navigationBarView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item_home) {
                return true;
            }

            if (itemId == R.id.item_community) {
                return true;
            }

            if (itemId == R.id.item_wishlist) {
                transferTo(WishlistFragment.newInstance("param1", "param2"));
                return true;
            }

            if (itemId == R.id.item_news) {
                return true;
            }

            return false;
        });

        navigationBarView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId() == R.layout.homepage) {
                }
            }
        });

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

    private void transferTo(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void startAutoSlide() {
        handler.postDelayed(runnable, DELAY_MS);
    }

    private void stopAutoSlide() {
        handler.removeCallbacks(runnable);
    }

}