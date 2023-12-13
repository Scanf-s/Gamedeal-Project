package com.ggamdeal.app.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggamdeal.app.R;
import com.ggamdeal.app.home.adapter.ActionElementAdapter;
import com.ggamdeal.app.home.adapter.AdventureElementAdapter;
import com.ggamdeal.app.home.adapter.CasualElementAdapter;
import com.ggamdeal.app.home.adapter.HorrorElementAdapter;
import com.ggamdeal.app.home.adapter.IndieElementAdapter;
import com.ggamdeal.app.home.adapter.PuzzleElementAdapter;
import com.ggamdeal.app.home.adapter.SimulationElementAdapter;
import com.ggamdeal.app.home.adapter.TopElementAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private NestedScrollView nestedScrollView;
    private FloatingActionButton fabScrollTop;
    private TopElementAdapter topAdapter;
    private ActionElementAdapter actionAdapter;
    private AdventureElementAdapter adventureAdapter;
    private CasualElementAdapter casualAdapter;
    private HorrorElementAdapter horrorAdapter;
    private IndieElementAdapter indieAdapter;
    private PuzzleElementAdapter puzzleAdapter;
    private SimulationElementAdapter simulationAdapter;

    private ViewPager2 homeTopViewPager;
    private RecyclerView actionRecyclerView;
    private RecyclerView adventureRecyclerView;
    private RecyclerView casualRecyclerView;
    private RecyclerView horrorRecyclerView;
    private RecyclerView indieRecyclerView;
    private RecyclerView puzzleRecyclerView;
    private RecyclerView simulationRecyclerView;

    private Handler handler = new Handler();
    private CircleIndicator3 indicator;
    private TextView showMore1;
    private TextView showMore2;
    private TextView showMore3;
    private TextView showMore4;
    private TextView showMore5;
    private TextView showMore6;
    private TextView showMore7;

    public HomeFragment() {}

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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

        //FloatingActionButton 설정
        fabScrollTop = view.findViewById(R.id.fabScrollTop);
        fabScrollTop.setOnClickListener(view1 -> {
            ObjectAnimator.ofInt(nestedScrollView, "scrollY", 0).setDuration(500).start();
        });

        // ViewPager 설정
        homeTopViewPager = view.findViewById(R.id.homeTopViewPager);
        topAdapter = new TopElementAdapter(this);
        homeTopViewPager.setAdapter(topAdapter);

        // RecyclerView 설정
        actionRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView);
        actionAdapter = new ActionElementAdapter(this);
        actionRecyclerView.setAdapter(actionAdapter);

        adventureRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView2);
        adventureAdapter = new AdventureElementAdapter(this);
        adventureRecyclerView.setAdapter(adventureAdapter);

        casualRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView3);
        casualAdapter = new CasualElementAdapter(this);
        casualRecyclerView.setAdapter(casualAdapter);

        horrorRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView4);
        horrorAdapter = new HorrorElementAdapter(this);
        horrorRecyclerView.setAdapter(horrorAdapter);

        indieRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView5);
        indieAdapter = new IndieElementAdapter(this);
        indieRecyclerView.setAdapter(indieAdapter);

        puzzleRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView6);
        puzzleAdapter = new PuzzleElementAdapter(this);
        puzzleRecyclerView.setAdapter(puzzleAdapter);

        simulationRecyclerView = view.findViewById(R.id.homeMiddleRecyclerView7);
        simulationAdapter = new SimulationElementAdapter(this);
        simulationRecyclerView.setAdapter(simulationAdapter);

        // CircleIndicator 설정
        indicator = view.findViewById(R.id.indicatior);
        indicator.setViewPager(homeTopViewPager);
        topAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
        indicator.setVisibility(View.VISIBLE);

        //더보기 버튼 설정
        showMore1 = view.findViewById(R.id.homeMiddleTextTitleSecondary);
        showMore2 = view.findViewById(R.id.homeMiddleTextTitleSecondary2);
        showMore3 = view.findViewById(R.id.homeMiddleTextTitleSecondary3);
        showMore4 = view.findViewById(R.id.homeMiddleTextTitleSecondary4);
        showMore5 = view.findViewById(R.id.homeMiddleTextTitleSecondary5);
        showMore6 = view.findViewById(R.id.homeMiddleTextTitleSecondary6);
        showMore7 = view.findViewById(R.id.homeMiddleTextTitleSecondary7);

        Log.d("AutoSlide", "AutoSlide 실행됨");
        startAutoSlide();

        showMoreButtonListener(showMore1, actionRecyclerView, "Action");
        showMoreButtonListener(showMore2, adventureRecyclerView, "Adventure");
        showMoreButtonListener(showMore3, casualRecyclerView, "Casual");
        showMoreButtonListener(showMore4, horrorRecyclerView, "Horror");
        showMoreButtonListener(showMore5, indieRecyclerView, "Indie");
        showMoreButtonListener(showMore6, puzzleRecyclerView, "Puzzle");
        showMoreButtonListener(showMore7, simulationRecyclerView, "Simulation");
    }

    private void showMoreButtonListener(TextView showMore, RecyclerView recyclerView, String defaultText) {
        showMore.setOnClickListener(v -> {
            boolean isExpanded = recyclerView.getVisibility() == View.VISIBLE;
            float targetAlpha = isExpanded ? 0f : 1f;
            int visibilityAfterAnimation = isExpanded ? View.GONE : View.VISIBLE;

            recyclerView.animate()
                    .alpha(targetAlpha)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerView.setVisibility(visibilityAfterAnimation);
            }
        });

            showMore.setText(isExpanded ? "더보기" : "닫기");
    });
}

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
}