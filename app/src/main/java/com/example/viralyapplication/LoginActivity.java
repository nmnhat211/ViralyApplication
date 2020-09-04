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
    private LinearLayout mLinearLayoutParent;
    private CheckBox mCheckBoxRememberMe;
    private TextView mTextViewForgotPassword, mTextViewEmptyUsername, mTextViewEmptyUsernameAndPassword;
    private EditText mEditTextEmail, mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView() {
        mLinearLayoutParent = findViewById(R.id.linear_login);

        mTextViewEmptyUsernameAndPassword = findViewById(R.id.text_view_empty_username_and_password);
        mTextViewEmptyUsername = findViewById(R.id.text_view_empty_username);

        mTextViewEmptyUsernameAndPassword.setVisibility(View.GONE);
        mTextViewEmptyUsername.setVisibility(View.GONE);

        mEditTextEmail = findViewById(R.id.edit_text_mail);
        mEditTextPassword = findViewById(R.id.edit_text_password);

        mButtonLogin = findViewById(R.id.button_login);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        //Remove focus for EditText
        mLinearLayoutParent.setOnTouchListener(new View.OnTouchListener() {
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

        //Handle button login
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUsername, mPassword;
                mUsername = mEditTextEmail.getText().toString();
                mPassword = mEditTextPassword.getText().toString();
                checkEmpty(mUsername, mPassword);
                mEditTextEmail.clearFocus();
                mEditTextPassword.clearFocus();
            }
        });
    }

    private void checkEmpty(String username, String password) {

        if (username.equals("") && password.equals("")) {
            mTextViewEmptyUsernameAndPassword.setVisibility(View.VISIBLE);
            mTextViewEmptyUsername.setVisibility(View.GONE);
        } else if (username.equals("")) {
            mTextViewEmptyUsername.setVisibility(View.VISIBLE);
            mTextViewEmptyUsername.setText(R.string.empty_username);
            mTextViewEmptyUsernameAndPassword.setVisibility(View.GONE);
        } else if (password.equals("")) {
            mTextViewEmptyUsername.setVisibility(View.VISIBLE);
            mTextViewEmptyUsername.setText(R.string.empty_password);
            mTextViewEmptyUsernameAndPassword.setVisibility(View.GONE);
        }
    }

}