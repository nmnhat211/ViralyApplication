package com.example.viralyapplication.api;

import com.example.viralyapplication.model.login.ModelLogin;
import com.example.viralyapplication.model.email.EmailVerify;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<ModelLogin> loginAccount(@Body Map<String, String> body_account);

    @GET("users/verify")
    Call<EmailVerify> getVerify();
}
