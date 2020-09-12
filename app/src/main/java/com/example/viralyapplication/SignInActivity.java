package com.example.viralyapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    private Button mButtonLogin, mButtonCheckVerify;
    private LinearLayout mLinearLayoutParent;
    private CheckBox mCheckBoxRememberMe;
    private TextView mTxtSignUpNow, mTxtForgotPassword;
    private TextInputLayout mTextInputEmail, mTextInputPassword;
    private EditText mEditTextEmail, mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        initEvent();
        EmptyEditText();
    }

    private void initView() {
        mLinearLayoutParent = findViewById(R.id.linear_login);

        mTxtSignUpNow = findViewById(R.id.text_view_sign_up_now);

        mTextInputEmail = findViewById(R.id.text_input_mail);
        mTextInputPassword = findViewById(R.id.text_input_password);

        mEditTextEmail = findViewById(R.id.edit_text_email);
        mEditTextPassword = findViewById(R.id.edit_text_password);

        mButtonLogin = findViewById(R.id.button_login_account);
        mButtonCheckVerify = findViewById(R.id.button_check_verify_account);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
//        Remove focus for EditText
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
                String mEmail, mPassword;
//                mEmail = mTextInputEmail.getEditText().getText().toString();
//                mPassword = mTextInputPassword.getEditText().getText().toString();
                mEmail = "nmnhat";
                mPassword = "nmnhat";
                loginAuthAccount(mEmail, mPassword);
                mEditTextEmail.clearFocus();
                mEditTextPassword.clearFocus();
            }
        });

        mButtonCheckVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVerify();
            }
        });

        //Pass to sign up screen
        mTxtSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mTransferScreen = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(mTransferScreen);
            }
        });

    }

    private void EmptyEditText() {
        mEditTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (mEditTextEmail.getText().toString().trim().length() == 0) {
                        mTextInputEmail.setError(getText(R.string.empty_email));
                    }
                } else {
                    mTextInputEmail.setError(null);
                }
            }
        });
        mEditTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (mEditTextPassword.getText().toString().trim().length() == 0) {
                        mTextInputPassword.setError(getText(R.string.empty_password));
                    }
                } else {
                    mTextInputPassword.setError(null);
                }
            }
        });
    }

//    private void updateBackgroundColor(EditText editText, ) {
//        editText.getBackground().setColorFilter(getColor(R.color.colorWhite));
//    }


    private void loginAuthAccount(String username, String password) {
        ApiLoginScreen mLoginApi = NetworkProfile.getRetrofitInstance().create(ApiLoginScreen.class);

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", username);
        requestBody.put("password", password);

        Call<ModelLogin> callRequest = mLoginApi.loginAccount(requestBody);
        callRequest.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (response.code() == 200) {
                    Log.e("status", "" + response.raw().headers().toString());

                    Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("status: ", "" + t.getMessage());
            }
        });
    }

    private void checkVerify() {
        ApiLoginScreen mCheckVerify = NetworkProfile.getRetrofitInstance().create(ApiLoginScreen.class);
        Call<ModelVerify> call = mCheckVerify.getVerify();
        call.enqueue(new Callback<ModelVerify>() {
            @Override
            public void onResponse(Call<ModelVerify> call, Response<ModelVerify> response) {
                if (response.code() == 200) {
                    Toast.makeText(SignInActivity.this, "Get verify successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelVerify> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Connect error", Toast.LENGTH_SHORT).show();
                Log.e("status", "" + t.getMessage());
            }
        });
    }
}