package com.example.viralyapplication.utility;

public class Constrain {
    public static final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String PASSWORD_REGEX = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*;-]).{4,}";
    public static final String IS_ACCOUNT_CREATED = "is-account-created";
    public static final String IS_ACCOUNT_LOGGED = "is-account-logged";
    public static final String IS_ACCOUNT_LOGGED_OUT = "is-account-logged_out";
    public static String BASE_URL = "http://viraly-server.herokuapp.com/";
    public static final int IS_SUCCESS = 200;
    public static final int IS_BAB_REQUEST = 400;
    public static final int IS_UNAUTHORIZED = 401;
    public static final int IS_FORBIDDEN = 403;
    public static final String IS_OK = "ok";
}
