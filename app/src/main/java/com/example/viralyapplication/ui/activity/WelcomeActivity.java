package com.example.viralyapplication.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.viralyapplication.R;
import com.example.viralyapplication.utility.Utils;

public class WelcomeActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
    }

    private void initView() {
        Button btnCreateAccount = findViewById(R.id.button_of_bottom_welcome_screen);
        Button btnSigInAccount = findViewById(R.id.button_create_account_welcome);
        btnCreateAccount.setOnClickListener(this);
        btnSigInAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_of_bottom_welcome_screen:
                Utils.goToSigInActivity(this);
                finish();
                break;
            case R.id.button_create_account_welcome:
                Utils.goToSigUpActivity(this);
                finish();
                break;
        }
    }
}