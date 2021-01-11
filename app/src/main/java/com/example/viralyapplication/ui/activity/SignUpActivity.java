package com.example.viralyapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.api.LoginApi;
import com.example.viralyapplication.repository.model.UserModel;
import com.example.viralyapplication.utility.Constrain;
import com.example.viralyapplication.utility.NetworkProfile;
import com.example.viralyapplication.utility.Utils;
import com.example.viralyapplication.utility.messageModel;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends ToolbarActivity {
    private Button btnCreateAccount, btnTextSignUp;
    private RelativeLayout rlParen;
    private EditText edtUserName, edtDisplayName, edtEmailAddress, edtPassword;
    private TextInputLayout txtInputUserName, txtInputDisplayName, txtInputEmailAddress, txtInputPassword;
    private boolean isUsernameEmpty = true, isNameEmpty = true, isMailEmpty = true, isPasswordEmpty = true;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_layout);
        loadView();
        initEvent();
        EmptyEditText();
    }

    private void initView() {
        setDisplayBack(true);
        setDisplayTitle(false);
        setTitleToolbar(getString(R.string.title_sign_up));

        btnTextSignUp = findViewById(R.id.button_of_bottom_welcome_screen);
        btnCreateAccount = findViewById(R.id.button_create_account);
        edtUserName = findViewById(R.id.edit_text_name);
        edtDisplayName = findViewById(R.id.edit_text_display_name);
        edtEmailAddress = findViewById(R.id.edit_text_email);
        edtPassword = findViewById(R.id.edit_text_password);

        txtInputUserName = findViewById(R.id.text_input_name);
        txtInputDisplayName = findViewById(R.id.text_input_display_name);
        txtInputEmailAddress = findViewById(R.id.text_input_mail);
        txtInputPassword = findViewById(R.id.text_input_password);

        rlParen = findViewById(R.id.root_signup);

        btnTextSignUp.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
    }

    private void loadView() {
        initView();
        edtEmailAddress.setText("");
        edtPassword.setText("");
        btnTextSignUp.setText(getText(R.string.title_sign_in));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
//        Remove focus for EditText
        rlParen.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (edtUserName.isFocused() || edtPassword.isFocused()
                        || edtEmailAddress.isFocused() || edtDisplayName.isFocused()) {
                    Rect outRect = new Rect();
                    edtUserName.getGlobalVisibleRect(outRect);
                    edtPassword.getGlobalVisibleRect(outRect);
                    edtEmailAddress.getGlobalVisibleRect(outRect);
                    edtDisplayName.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        edtUserName.clearFocus();
                        edtPassword.clearFocus();
                        edtEmailAddress.clearFocus();
                        edtDisplayName.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
            return false;
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_of_bottom_welcome_screen:
                Utils.goToSigInActivity(mContext);
                finish();
                break;
            case R.id.button_create_account:
                checkNull();
                break;
        }
    }

    private void createAccount(String name,
                               String username,
                               String mail,
                               String password) {

        showProgressDialog();
        LoginApi createAccount = NetworkProfile.getRetrofitInstance().create(LoginApi.class);
//        RequestBody mName = RequestBody.create(MultipartBody.FORM, name);
//        RequestBody mUsername = RequestBody.create(MultipartBody.FORM, username);
//        RequestBody mMail = RequestBody.create(MultipartBody.FORM, mail);
//        RequestBody mPassword = RequestBody.create(MultipartBody.FORM, password);
        messageModel errorMessage = null;
        Call<UserModel> callApi = createAccount.registerUser(mail, username, name, password);
        callApi.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                dismissProgressDialog();
                switch (response.code()) {
                    case Constrain.IS_SUCCESS:
                        Toast.makeText(SignUpActivity.this, getString(R.string.create_successfully), Toast.LENGTH_SHORT).show();
                        break;
                    case Constrain.IS_BAB_REQUEST:
                        Toast.makeText(SignUpActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SignUpActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkNull() {
        String username = "", name = "", mail = "", password = "", text = "is";
        int i = 0;
        if (!isUsernameEmpty && !isNameEmpty && !isMailEmpty && !isPasswordEmpty) {
            username = edtDisplayName.getText().toString().trim();
            name = edtDisplayName.getText().toString().trim();
            mail = edtDisplayName.getText().toString().trim();
            password = edtDisplayName.getText().toString().trim();
            createAccount(username, name, mail, password);

        } else {
            if (isUsernameEmpty) {
                username = getString(R.string.username_hint) + ",";
                i++;
            }
            if (isNameEmpty) {
                name = getString(R.string.name_hint) + ",";
                i++;
            }
            if (isMailEmpty) {
                mail = getString(R.string.email_hint) + ",";
                i++;
            }
            if (isPasswordEmpty) {
                password = getString(R.string.password_hint) + ",";
                i++;
            }
            if (i >= 2) {
                text = "are";
            }
            Toast.makeText(SignUpActivity.this, String
                            .format(getResources().
                                            getString(R.string.error_empty),
                                    username,
                                    name,
                                    mail,
                                    password,
                                    text),
                    Toast.LENGTH_SHORT).
                    show();
        }
    }


    private void EmptyEditText() {
        edtUserName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtUserName.getText().toString().trim().length() == 0) {
                    txtInputUserName.setError("");
                    isUsernameEmpty = true;
                }
            } else {
                txtInputUserName.setError(null);
                isUsernameEmpty = false;
            }
        });

        edtDisplayName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtDisplayName.getText().toString().trim().length() == 0) {
                    txtInputDisplayName.setError("");
                    isNameEmpty = true;
                }
            } else {
                txtInputDisplayName.setError(null);
                isNameEmpty = false;
            }
        });

        edtEmailAddress.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtEmailAddress.getText().toString().trim().length() == 0) {
                    txtInputEmailAddress.setError("");
                    isMailEmpty = true;

                }
            } else {
                txtInputEmailAddress.setError(null);
                isMailEmpty = false;
            }
        });

        edtPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtPassword.getText().toString().trim().length() == 0) {
                    txtInputPassword.setError("");
                    isPasswordEmpty = true;
                }
            } else {
                txtInputPassword.setError(null);
                isPasswordEmpty = false;
            }
        });
    }

}