package com.example.viralyapplication;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiLoginScreen {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<ModelLogin> loginAccount(@Body Map<String, String> body_account);

    @GET("users/verify")
    Call<ModelVerify> getVerify();
}
