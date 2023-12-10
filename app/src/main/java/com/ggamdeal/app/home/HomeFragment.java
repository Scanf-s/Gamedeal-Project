package com.ggamdeal.app.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ggamdeal.app.R;
import com.ggamdeal.app.home.adapter.ActionElementAdapter;
import com.ggamdeal.app.home.adapter.CasualElementAdpater;
import com.ggamdeal.app.home.adapter.HorrorElementAdapter;
import com.ggamdeal.app.home.adapter.IndieElementAdapter;
import com.ggamdeal.app.home.adapter.TopElementAdapter;
import com.ggamdeal.app.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.relex.circleindicator.CircleIndicator3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Toolbar toolbar;
    private TopElementAdapter topAdapter;
    private ActionElementAdapter actionAdapter;
    private CasualElementAdpater casualAdapter;
    private HorrorElementAdapter horrorAdapter;
    private IndieElementAdapter indieAdapter;

    private ViewPager2 homeTopViewPager;
    private RecyclerView actionRecyclerView;
    private RecyclerView casualRecyclerView;
    private RecyclerView horrorRecyclerView;
    private RecyclerView indieRecyclerView;

    private Handler handler = new Handler();

    ActionBarDrawerToggle toggle;
    CircleIndicator3 indicator;
    DrawerLayout drawerLayout;

    TextView showMore1;
    TextView showMore2;
    TextView showMore3;
    TextView showMore4;

    NavigationView navigationView;
    FirebaseUser currentUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationView = view.findViewById(R.id.navigationView);
        // ViewPager 설정
        homeTopViewPager = view.findViewById(R.id.homeTopViewPager);
        topAdapter = new TopElementAdapter(this);
        homeTopViewPager.setAdapter(topAdapter);

        actionRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView);
        actionAdapter = new ActionElementAdapter(this);
        actionRecyclerView.setAdapter(actionAdapter);

        casualRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView2);
        casualAdapter = new CasualElementAdpater(this);
        casualRecyclerView.setAdapter(casualAdapter);

        horrorRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView3);
        horrorAdapter = new HorrorElementAdapter(this);
        horrorRecyclerView.setAdapter(horrorAdapter);

        indieRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView4);
        indieAdapter = new IndieElementAdapter(this);
        indieRecyclerView.setAdapter(indieAdapter);

        // CircleIndicator 설정
        indicator = view.findViewById(R.id.homeTopElementPageIndicator);
        indicator.setViewPager(homeTopViewPager);

        drawerLayout = view.findViewById(R.id.drawerLayout);

        //더보기 버튼 설정
        showMore1 = view.findViewById(R.id.homeMiddleTextTitleSecondary);
        showMore2 = view.findViewById(R.id.homeMiddleTextTitleSecondary2);
        showMore3 = view.findViewById(R.id.homeMiddleTextTitleSecondary3);
        showMore4 = view.findViewById(R.id.homeMiddleTextTitleSecondary4);

        toolbar = view.findViewById(R.id.toolbar);
        // Access the activity and set the toolbar
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        //Firebase 설정
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("Firebase", "현재 로그인 된 유저의 정보를 가져옵니다.");

        Log.d("AutoSlide", "AutoSlide 실행됨");
        startAutoSlide();

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

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            View headerView = navigationView.getHeaderView(0);
            TextView userName = headerView.findViewById(R.id.username);
            userName.setText(userEmail);
        }
    }
    /**
     * 자동으로 다음 페이지로 넘어갈 수 있도록 만드는 함수
     */
    private Runnable runnable = new Runnable() {
        public void run() {
            if (topAdapter.getItemCount() > 0) {
                int currentPosition = homeTopViewPager.getCurrentItem();
                int nextPosition = (currentPosition + 1) % topAdapter.getItemCount();
                homeTopViewPager.setCurrentItem(nextPosition);
            }
            // 3초
            int PERIOD_MS = 3000;
            handler.postDelayed(this, PERIOD_MS);
        }
    };

    private void startAutoSlide() {
        // 3초
        int DELAY_MS = 3000;
        handler.postDelayed(runnable, DELAY_MS);
    }

    private void stopAutoSlide() {
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAutoSlide(); // 자동 슬라이딩 정리
    }

    private void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        if (firebaseAuth.getCurrentUser() == null) {
            Log.d("Firebase", "signOut:success");

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
        else Log.d("Firebase", "signOut:failure");
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}