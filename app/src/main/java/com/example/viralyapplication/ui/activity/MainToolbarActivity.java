package com.example.viralyapplication.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.viralyapplication.R;

public class MainToolbarActivity extends AppCompatActivity {
    private TextView tvMainTitle;

    protected Context mContext;

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main_toolbar_layout, null);
//        FrameLayout activityContent = fullView.findViewById(R.id.);
//        getLayoutInflater().inflate(layoutResID, , true);
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
    }

    protected void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            setDisplayTitle(false);
        } else {
            setDisplayTitle(true);
            tvMainTitle.setText(title);
        }
    }

    protected void setDisplayTitle(boolean value) {
        tvMainTitle.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}
