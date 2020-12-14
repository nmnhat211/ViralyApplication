package com.example.viralyapplication.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.viralyapplication.ui.activity.SignInActivity;
import com.example.viralyapplication.ui.activity.SignUpActivity;

public class Utils {
    public static Context mContext;

    public static void showKeyBoard(View view, boolean isShow) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    public static void goToSigUpActivity(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    public static void goToSigInActivity(Context context){
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }
}
