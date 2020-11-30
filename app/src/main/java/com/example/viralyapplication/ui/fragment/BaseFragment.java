package com.example.viralyapplication.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import android.view.View.OnClickListener;

import com.example.viralyapplication.R;

public class BaseFragment extends Fragment implements OnClickListener {


    protected Context mContext;
    private AlertDialog alertDialog;
    protected Dialog mProgress;

    public void showProgressDialog() {
        if (mProgress == null) {
            mProgress = new Dialog(this.getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
            mProgress.setContentView(R.layout.progressing_dialog_layout);
            mProgress.setCancelable(false);
        } else {
            mProgress.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
            mProgress = null;
        }
    }



    @Override
    public void onClick(View v) {

    }
}
