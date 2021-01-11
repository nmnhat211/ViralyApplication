package com.example.viralyapplication.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.model.UserModel;
import com.example.viralyapplication.ui.activity.SignInActivity;
import com.example.viralyapplication.ui.activity.SignUpActivity;
import com.example.viralyapplication.ui.fragment.NewFeedFragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {
    public static Context mContext;
    private DialogListener mDialogListener;

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

    public static void goToSigUpActivity(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    public static void goToSigInActivity(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    public static void goToNewFeedFragment(Context context, UserModel userModel) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtra(NewFeedFragment.KEY_USER_DATA, userModel);
        context.startActivity(intent);
    }

    public static void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    public static boolean isNull(String string){
        return string.isEmpty() || string.equals("null");
    }


    public static String isError(int status){
        String errorMessages = "null";
        switch (status){
            case Constrain.IS_SUCCESS:
                break;
            case Constrain.IS_FORBIDDEN:
                errorMessages = Resources.getSystem().getString(R.string.account_baned_text);
                break;
            default:
                return errorMessages;
        }
        return errorMessages;
    }

    public static String convertDate(String content) {
        String covertDate = "";
        Date date;

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = input.parse(content);
            covertDate = output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return covertDate;
    }

    public static String convertFloat2f(float context) {
        DecimalFormat format = new DecimalFormat("#.00");
        return format.format(context);
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void deleteAlert(Context context, String message, String title,int position, String id, DialogListener dialogListener){
        mDialogListener = dialogListener;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvDeleteTitle = dialog.findViewById(R.id.tv_title_dialog);
        TextView tvDeleteMessage = dialog.findViewById(R.id.tv_content_dialog);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        Button btnPositive = dialog.findViewById(R.id.btn_delete);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogListener.onAcceptClickListener(dialog, position, id);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        tvDeleteTitle.setText(title);
        tvDeleteMessage.setText(message);
        dialog.show();
    }
}
