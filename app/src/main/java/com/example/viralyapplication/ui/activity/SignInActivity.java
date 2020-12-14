package com.example.viralyapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viralyapplication.repository.api.LoginApi;
import com.example.viralyapplication.repository.model.LoginModel;
import com.example.viralyapplication.repository.model.EmailVerifyModel;
import com.example.viralyapplication.R;
import com.example.viralyapplication.utility.NetworkProfile;
import com.example.viralyapplication.utility.Utils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends BaseFragmentActivity {
    private Button btnLogin, btBottom;
    private RelativeLayout llParent;
    private CheckBox cbxRememberMe;
    private TextView tvSignUpNow, tvForgotPassword;
    private TextInputLayout mTextInputEmail, mTextInputPassword;
    private EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_layout);
        initView();
        initEvent();
        EmptyEditText();
    }

    private void initView() {
        llParent = findViewById(R.id.root_login);
        tvSignUpNow = findViewById(R.id.text_view_sign_up_now);
        tvForgotPassword = findViewById(R.id.text_view_forgot_password);
        mTextInputEmail = findViewById(R.id.text_input_mail);
        mTextInputPassword = findViewById(R.id.text_input_password);
        edtEmail = findViewById(R.id.edit_text_email);
        edtPassword = findViewById(R.id.edit_text_password);
        btnLogin = findViewById(R.id.button_login_account);
        btBottom = findViewById(R.id.button_of_bottom_welcome_screen);
        cbxRememberMe = findViewById(R.id.cbx_remember);

        tvSignUpNow.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btBottom.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_login_account:
                String mEmail, mPassword;
                mEmail = edtEmail.getText().toString();
                mPassword = edtPassword.getText().toString();
                loginAuthAccount(mEmail, mPassword);
                edtEmail.clearFocus();
                edtPassword.clearFocus();
                break;
            case R.id.text_view_forgot_password:
                Toast.makeText(mContext, "Content", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_view_sign_up_now:
            case R.id.button_of_bottom_welcome_screen:
                Utils.goToSigUpActivity(this);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
//        Remove focus for EditText
        llParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (edtEmail.isFocused() || edtPassword.isFocused()) {
                        Rect outRect = new Rect();
                        edtEmail.getGlobalVisibleRect(outRect);
                        edtPassword.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            edtEmail.clearFocus();
                            edtPassword.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
    }

    private void EmptyEditText() {
        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (edtEmail.getText().toString().trim().length() == 0) {
                        mTextInputEmail.setError(getText(R.string.empty_email));
                    }
                } else {
                    mTextInputEmail.setError(null);
                }
            }
        });
        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (edtPassword.getText().toString().trim().length() == 0) {
                        mTextInputPassword.setError(getText(R.string.empty_password));
                    }
                } else {
                    mTextInputPassword.setError(null);
                }
            }
        });
    }


    private void loginAuthAccount(String username, String password) {
        showProgressDialog();
        LoginApi mLoginApi = NetworkProfile.getRetrofitInstance().create(LoginApi.class);

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", username);
        requestBody.put("password", password);

        Call<LoginModel> callRequest = mLoginApi.loginAccount(requestBody);
        callRequest.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                dismissProgressDialog();
                if (response.code() == 200) {
                    Log.e("status", "" +response.body().getIsAuthenticated());

                    Toast.makeText(SignInActivity.this, R.string.login_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, R.string.unknown_username, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SignInActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("status: ", "" + t.getMessage());
            }
        });
    }

    private void checkVerify() {
        LoginApi mCheckVerify = NetworkProfile.getRetrofitInstance().create(LoginApi.class);
        Call<EmailVerifyModel> call = mCheckVerify.getVerify();
        call.enqueue(new Callback<EmailVerifyModel>() {
            @Override
            public void onResponse(Call<EmailVerifyModel> call, Response<EmailVerifyModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(SignInActivity.this, R.string.successfully_verify_status, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, R.string.failed_verify_status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmailVerifyModel> call, Throwable t) {
                Toast.makeText(SignInActivity.this, R.string.cant_connect_to_server_status, Toast.LENGTH_SHORT).show();
                Log.e("status", "" + t.getMessage());
            }
        });
    }

}