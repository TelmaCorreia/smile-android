package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.request.LoginRequest;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.data.remote.models.response.LoginResponse;
import com.thesis.smile.data.remote.models.response.UserResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {

    @GET("users/{token}")
    Single<Response<UserResponse>> userWithId(@Path("token") String userId);

    @PUT("users/{userUuid}")
    Single<Response<UserResponse>> updateUserWithId(@Path("userUuid") String userId, @Body UserRemote user);

    @Multipart
    @PUT("users/{userUuid}/image")
    Single<Response<UserResponse>> updateUserImage(@Path("userUuid") String userId, @Part(encoding = "form-data") MultipartBody.Part image);

}
