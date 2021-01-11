package com.example.viralyapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viralyapplication.repository.CallApi;
import com.example.viralyapplication.repository.api.LoginApi;
import com.example.viralyapplication.repository.model.LoginModel;
import com.example.viralyapplication.repository.model.EmailVerifyModel;
import com.example.viralyapplication.R;
import com.example.viralyapplication.ui.event.SigInEvent;
import com.example.viralyapplication.utility.NetworkProfile;
import com.example.viralyapplication.utility.Utils;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends BaseFragmentActivity {

    private Button btnLogin, btBottom;
    private RelativeLayout rlParent;
    private CheckBox cbxRememberMe;
    private TextView tvSignUpNow, tvForgotPassword;
    private TextInputLayout mTextInputEmail, mTextInputPassword;
    private EditText edtEmail, edtPassword;
    private boolean isChecked;
    private SharedPreferences mPerPreferences;
    private String mUsername, mPassword;

    private static final String PREFS_NAME = "prefs_name";
    private static final String KEY_REMEMBER_ME = "key_remember_me";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_USERNAME = "key_username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_sign_in_layout);
        loadData();
        initEvent();
        EmptyEditText();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        cbxRememberMe = findViewById(R.id.cbx_remember);
        rlParent = findViewById(R.id.root_login);
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
        cbxRememberMe.setOnClickListener(this);
    }

    private void loadData() {
        initView();
        btBottom.setText(getText(R.string.title_sign_up));
        cbxRememberMe.setChecked(false);
        mPerPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isChecked = mPerPreferences.getBoolean(KEY_REMEMBER_ME, false);
        mUsername = mPerPreferences.getString(KEY_USERNAME, "");
        mPassword = mPerPreferences.getString(KEY_PASSWORD, "");
        if (isChecked) {
            edtEmail.setText(mUsername);
            edtPassword.setText(mPassword);
            cbxRememberMe.setChecked(true);
            loginAuthAccount(mUsername, mPassword);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_login_account:
                handledLogin();
                break;
            case R.id.text_view_forgot_password:
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
        rlParent.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (edtEmail.isFocused() || edtPassword.isFocused()) {
                    Rect outRect = new Rect();
                    edtEmail.getGlobalVisibleRect(outRect);
                    edtPassword.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        edtEmail.clearFocus();
                        edtPassword.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
            return false;
        });

        cbxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = compoundButton.isChecked();
            }
        });

    }


    private void EmptyEditText() {
        edtEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtEmail.getText().toString().trim().length() == 0) {
                    mTextInputEmail.setError(getText(R.string.empty_email));
                }
            } else {
                mTextInputEmail.setError(null);

            }
        });

        edtPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtPassword.getText().toString().trim().length() == 0) {
                    mTextInputPassword.setError(getText(R.string.empty_password));
                }
            } else {
                mTextInputPassword.setError(null);
            }
        });
    }

    private void handledLogin() {
        String mEmail, mPassword;
        mEmail = edtEmail.getText().toString();
        mPassword = edtPassword.getText().toString();
        loginAuthAccount(mEmail, mPassword);
        edtEmail.clearFocus();
        edtPassword.clearFocus();
    }


    private void loginAccount(String username, String password) {
        showProgressDialog();
        CallApi.loginAuthAccount(username, password, TAG);
    }

    @Subscribe
    public void getDataLogin(SigInEvent event) {
        if (!checkFilterName(event)) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (event.getStatus()) {
                    if (event.getUserData() != null) {
                        Log.e("ss", "aaaa");
                        Toast.makeText(mContext, "Login", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, event.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dismissProgressDialog();
                }
            }
        });
    }

    private void isRemember(String username, String password) {
        SharedPreferences logInPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (isChecked) {
            logInPreferences.edit()
                    .putBoolean(KEY_REMEMBER_ME, true)
                    .putString(KEY_USERNAME, username)
                    .putString(KEY_PASSWORD, password)
                    .apply();
            sendBroadcast(WelcomeActivity.KEY_AUTO_LOGIN);
        } else {
            logInPreferences.edit()
                    .clear()
                    .apply();

        }
    }


    private void loginAuthAccount(String username, String password) {
        showProgressDialog();
        LoginApi mLoginApi = NetworkProfile.getRetrofitInstance().create(LoginApi.class);

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", username);
        requestBody.put("password", password);

        Call<LoginModel> callRequest = mLoginApi.loginAccount(requestBody);
        callRequest.enqueue(new Callback<LoginModel>() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                dismissProgressDialog();
                int code = response.code();
                if (code == 200) {
                    isRemember(username, password);
                    Toast.makeText(SignInActivity.this, R.string.login_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, R.string.unknown_account, Toast.LENGTH_SHORT).show();
                }
                Log.e(String.format(getResources().getString(R.string.status_code), code), "" + response.message());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SignInActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure ", "" + t.getMessage());
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