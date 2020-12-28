package com.example.viralyapplication.ui.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.viralyapplication.R;

public class MainToolbarActivity extends BaseFragmentActivity {
    private TextView tvMainTitle;
    private ImageView ivActionBack, ivActionSearch, ivActionMenuBar;
    protected Context mContext;

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main_toolbar_layout, null);
        FrameLayout activityContent = fullView.findViewById(R.id.fl_fragment);
        getLayoutInflater().inflate(layoutResID, activityContent, true);
        super.setContentView(fullView);
        initView();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        tvMainTitle = findViewById(R.id.main_title_toolbar);
        ivActionBack = findViewById(R.id.iv_back_tool_bar);
        ivActionSearch = findViewById(R.id.iv_search_tool_bar);
        ivActionMenuBar = findViewById(R.id.iv_menu_bar);

        ivActionBack.setOnClickListener(this);
        ivActionSearch.setOnClickListener(this);
        ivActionMenuBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back_tool_bar:
                Toast.makeText(mContext, "back", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_menu_bar:
                Toast.makeText(mContext, "menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_search_tool_bar:
                Toast.makeText(mContext, "Search", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            setDisplayTitle(false);
        } else {
            setDisplayTitle(true);
            tvMainTitle.setText(title);
        }
    }


    protected void setDisplayMenu(boolean value) {
        ivActionMenuBar.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setDisplayBack(boolean value) {
        ivActionBack.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setDisplaySearch(boolean value) {
        ivActionBack.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setFragment(Fragment fragment) {
        super.setContentView(R.layout.activity_main_toolbar_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment, fragment)
                .addToBackStack(null)
                .commit();
        initView();
    }

    protected void setFragment(android.app.Fragment fragment) {
        super.setContentView(R.layout.activity_main_toolbar_layout);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment, fragment);
        transaction.commit();
        initView();
    }

    protected void setFragment(Fragment fragment, String tag) {
        super.setContentView(R.layout.activity_main_toolbar_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment, fragment, tag)
                .addToBackStack(null)
                .commit();
        initView();
    }

    /**
     * Add more fragment
     *
     * @param fragment
     */
    protected void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    protected void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    protected void removeFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment oldFragment = fm.findFragmentByTag(tag);
        fm.beginTransaction().remove(oldFragment).commitAllowingStateLoss();
        fm.popBackStack();
    }

    protected void setDisplayTitle(boolean value) {
        tvMainTitle.setVisibility(value ? View.VISIBLE : View.GONE);
    }

}

