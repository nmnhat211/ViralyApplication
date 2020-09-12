package com.example.viralyapplication.utils;

public class Constants {
    private static Constants mConstants;

    public static Constants getInstance() {
        if (mConstants == null) {
            mConstants = new Constants();
        }
        return mConstants;
    }

    public static final String EMAIL_VALID = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String PASSWORD_VALID = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*;-]).{4,}";
}
