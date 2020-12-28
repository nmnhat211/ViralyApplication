package com.example.viralyapplication.ui.activity;

import android.widget.FrameLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.viralyapplication.R;
import com.example.viralyapplication.ui.fragment.MainHomeFragment;
import com.example.viralyapplication.ui.fragment.NotifyFragment;
import com.example.viralyapplication.ui.fragment.ProfileFragment;
import com.example.viralyapplication.ui.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainHomeActivity extends MainToolbarActivity {

    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main_home_layout, null);
        FrameLayout activityContent = fullView.findViewById(R.id.fl_fragment_main_home);
        getLayoutInflater().inflate(layoutResID, activityContent, true);
        super.setContentView(fullView);
        initView();
    }

    @Override
    protected void setDisplayMenu(boolean value) {
        super.setDisplayMenu(true);
    }

    @Override
    protected void setDisplayTitle(boolean value) {
        super.setDisplayTitle(true);
    }

    @Override
    protected void setTitle(String title) {
        super.setTitle(R.string.home);
    }


    private void initView(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_root);
        bottomNavigationView.setOnNavigationItemSelectedListener(navLister);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navLister
            = menuItem -> {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new MainHomeFragment();
                break;
            case R.id.nav_notify:
                selectedFragment = new NotifyFragment();
                break;
            case R.id.nav_profile:
                selectedFragment = new ProfileFragment();
                break;
            case R.id.nav_search:
                selectedFragment = new SearchFragment();
                break;
        }
        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, selectedFragment).commit();
        return false;
    };
}
