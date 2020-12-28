package com.example.viralyapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.viralyapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends MainToolbarActivity {
    private Button btnCreateAccount;
    private RelativeLayout rlParen;
    private EditText edtUserName, edtDisplayName, edtEmailAddress, edtPassword;
    private TextInputEditText txtInputUserName, txtInputDisplayName, txtInputEmailAddress, txtInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_layout);
        initView();
        initEvent();
        EmptyEditText();
    }

    private void initView() {
        edtUserName = findViewById(R.id.edit_text_name);
        edtDisplayName = findViewById(R.id.edit_text_display_name);
        edtEmailAddress = findViewById(R.id.edit_text_email);
        edtPassword = findViewById(R.id.edit_text_password);

        txtInputUserName = findViewById(R.id.text_input_name);
        txtInputUserName = findViewById(R.id.text_input_display_name);
        txtInputUserName = findViewById(R.id.text_input_mail);
        txtInputUserName = findViewById(R.id.text_input_password);

        rlParen = findViewById(R.id.root_signup);
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

    private void EmptyEditText() {
        edtUserName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtUserName.getText().toString().trim().length() == 0) {
                    txtInputUserName.setError(getText(R.string.empty_email));
                }
            } else {
                txtInputUserName.setError(null);
            }
        });

        edtDisplayName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtDisplayName.getText().toString().trim().length() == 0) {
                    txtInputDisplayName.setError(getText(R.string.empty_display_name));
                }
            } else {
                txtInputDisplayName.setError(null);
            }
        });

        edtEmailAddress.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtEmailAddress.getText().toString().trim().length() == 0) {
                    txtInputEmailAddress.setError(getText(R.string.empty_display_name));
                }
            } else {
                txtInputEmailAddress.setError(null);
            }
        });

        edtPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtPassword.getText().toString().trim().length() == 0) {
                    txtInputPassword.setError(getText(R.string.empty_display_name));
                }
            } else {
                txtInputPassword.setError(null);
            }
        });

    }


}