package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.request.LoginRequest;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.data.remote.models.response.LoginResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface LoginApi {

    @POST("auth/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest registerRequest);

    @POST("auth/register")
    Single<Response<LoginResponse>> register(@Body RegisterRequest registerRequest);

    @Multipart
    @POST("auth/register")
    Single<Response<LoginResponse>> register(@Body RegisterRequest request, @Part(encoding = "form-data") MultipartBody.Part image);
}
