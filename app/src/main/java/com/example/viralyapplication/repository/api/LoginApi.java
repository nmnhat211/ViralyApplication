package com.example.viralyapplication.repository.api;

import com.example.viralyapplication.repository.model.LoginModel;
import com.example.viralyapplication.repository.model.EmailVerifyModel;
import com.example.viralyapplication.repository.model.UserModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<LoginModel> loginAccount(@Body Map<String, String> body_account);

    @Headers(("Content-Type: application/json"))
    @POST("")

    @GET("users/verify")
    Call<EmailVerifyModel> getVerify();

    @Headers("Content-Type: application/json")

    @Multipart
    @POST("users/register")
    Call<UserModel> registerUser(@Part("email") String name,
                                 @Part("username") String username,
                                 @Part("avatar") String avatar,
                                 @Part("display_name") String display_name,
                                 @Header("password") String password);

    @Multipart
    @POST("users/avatar")
    Call<UserModel> registerUserAvatar(@Part MultipartBody.Part image);
}
