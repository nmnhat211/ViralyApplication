package com.example.viralyapplication.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
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

import com.example.viralyapplication.api.LoginApi;
import com.example.viralyapplication.model.login.ModelLogin;
import com.example.viralyapplication.model.email.EmailVerify;
import com.example.viralyapplication.R;
import com.example.viralyapplication.utils.NetworkProfile;
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
//        mButtonCheckVerify = findViewById(R.id.button_check_verify_account);
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
//                nmnhat nmnhat;
                mEmail = "khoanguyen10110";
                mPassword = "khoanv";
                loginAuthAccount(mEmail, mPassword);
                mEditTextEmail.clearFocus();
                mEditTextPassword.clearFocus();
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


    private void loginAuthAccount(String username, String password) {
        LoginApi mLoginApi = NetworkProfile.getRetrofitInstance().create(LoginApi.class);

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", username);
        requestBody.put("password", password);

        Call<ModelLogin> callRequest = mLoginApi.loginAccount(requestBody);
        callRequest.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (response.code() == 200) {
                    Log.e("status", "" + response.raw().headers().toString());
                    Toast.makeText(SignInActivity.this, R.string.login_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, R.string.unknown_username, Toast.LENGTH_SHORT).show();
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
        LoginApi mCheckVerify = NetworkProfile.getRetrofitInstance().create(LoginApi.class);
        Call<EmailVerify> call = mCheckVerify.getVerify();
        call.enqueue(new Callback<EmailVerify>() {
            @Override
            public void onResponse(Call<EmailVerify> call, Response<EmailVerify> response) {
                if (response.code() == 200) {
                    Toast.makeText(SignInActivity.this, R.string.successfully_verify_status, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, R.string.failed_verify_status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmailVerify> call, Throwable t) {
                Toast.makeText(SignInActivity.this, R.string.cant_connect_to_server_status, Toast.LENGTH_SHORT).show();
                Log.e("status", "" + t.getMessage());
            }
        });
    }
}