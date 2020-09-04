package com.example.viralyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Button mButtonLogin;
    private LinearLayout mLayout;
    private CheckBox mCheckBoxRememberMe;
    private TextView mTextViewForgotPassword;
    private EditText mEditTextEmail, mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView() {
        mEditTextEmail = findViewById(R.id.edit_text_mail);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mLayout = findViewById(R.id.linear_login);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mEditTextEmail.isFocused() || mEditTextPassword.isFocused()) {
                        Rect outRect = new Rect();
                        mEditTextEmail.getGlobalVisibleRect(outRect);
                        mEditTextPassword.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mEditTextEmail.clearFocus();
                            mEditTextPassword.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

    }
}