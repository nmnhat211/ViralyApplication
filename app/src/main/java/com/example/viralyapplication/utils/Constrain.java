package com.example.viralyapplication.utils;

public class Constrain {
    public static final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String PASSWORD_REGEX = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*;-]).{4,}";
    public static final String IS_ACCOUNT_CREATED = "is-account-created";
    public static final String IS_ACCOUNT_LOGGED = "is-account-logged";
    public static final String IS_ACCOUNT_LOGGED_OUT = "is-account-logged_out";
}
