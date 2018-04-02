package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.request.LoginRequest;
import com.thesis.smile.data.remote.models.response.LoginResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("api/auth/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest registerRequest);

}
