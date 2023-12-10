package com.ggamdeal.app.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ggamdeal.app.R;
import com.ggamdeal.app.community.CommunityFragment;
import com.ggamdeal.app.news.NewsFragment;
import com.ggamdeal.app.wishlist.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        navigationBarView = findViewById(R.id.bottom_navigation);
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

    }

    private void transferTo(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}