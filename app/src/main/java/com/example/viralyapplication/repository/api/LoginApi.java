package com.example.viralyapplication.repository.api;

import com.example.viralyapplication.repository.model.LoginModel;
import com.example.viralyapplication.repository.model.EmailVerifyModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<LoginModel> loginAccount(@Body Map<String, String> body_account);

    @GET("users/verify")
    Call<EmailVerifyModel> getVerify();
}
