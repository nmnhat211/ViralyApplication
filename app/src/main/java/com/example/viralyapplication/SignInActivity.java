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

public class SignInActivity extends AppCompatActivity {
    private Button mButtonLogin;
    private LinearLayout mLinearLayoutParent;
    private CheckBox mCheckBoxRememberMe;
    private TextView mTxtForgotPassword, mTxtEmptyUsernameOrPassword, mTxtEmptyUsernameAndPassword;
    private EditText mEdtEmail, mEdtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        initEvent();
    }

    private void initView() {
        mLinearLayoutParent = findViewById(R.id.linear_login);

        mTxtEmptyUsernameAndPassword = findViewById(R.id.text_view_empty_username_and_password);
        mTxtEmptyUsernameOrPassword = findViewById(R.id.text_view_empty_username);

        mTxtEmptyUsernameAndPassword.setVisibility(View.GONE);
        mTxtEmptyUsernameOrPassword.setVisibility(View.GONE);

        mEdtEmail = findViewById(R.id.edit_text_mail_sign_up);
        mEdtPassword = findViewById(R.id.edit_text_password_sign_up);

        mButtonLogin = findViewById(R.id.button_login_account);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        //Remove focus for EditText
        mLinearLayoutParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mEdtEmail.isFocused() || mEdtPassword.isFocused()) {
                        Rect outRect = new Rect();
                        mEdtEmail.getGlobalVisibleRect(outRect);
                        mEdtPassword.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mEdtEmail.clearFocus();
                            mEdtPassword.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

        //Handle button login
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUsername, mPassword;
                mUsername = mEdtEmail.getText().toString();
                mPassword = mEdtPassword.getText().toString();
                checkEmpty(mUsername, mPassword);
                mEdtEmail.clearFocus();
                mEdtPassword.clearFocus();
            }
        });
    }

    private void checkEmpty(String username, String password) {
        mTxtEmptyUsernameOrPassword.setVisibility(View.GONE);
        mTxtEmptyUsernameAndPassword.setVisibility(View.GONE);

        if (username.equals("") && password.equals("")) {
            mTxtEmptyUsernameAndPassword.setVisibility(View.VISIBLE);
        } else if (username.equals("")) {
            mTxtEmptyUsernameOrPassword.setText(R.string.empty_username);
            mTxtEmptyUsernameOrPassword.setVisibility(View.VISIBLE);
        } else if (password.equals("")) {
            mTxtEmptyUsernameOrPassword.setText(R.string.empty_password);
            mTxtEmptyUsernameOrPassword.setVisibility(View.VISIBLE);
        }
    }

}