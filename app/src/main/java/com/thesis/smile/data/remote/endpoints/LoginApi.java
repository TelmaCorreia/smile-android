package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.request.ChangePasswordRequest;
import com.thesis.smile.data.remote.models.request.LoginRequest;
import com.thesis.smile.data.remote.models.request.RecoverPasswordStep1Request;
import com.thesis.smile.data.remote.models.request.RecoverPasswordStep2Request;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.data.remote.models.response.LoginResponse;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface LoginApi {

    @POST("auth/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest registerRequest);

    @POST("auth/register")
    Single<Response<LoginResponse>> register(@Body RegisterRequest registerRequest);

    @Multipart
    @POST("auth/register")
    Single<Response<LoginResponse>> register(@Body RegisterRequest request, @Part(encoding = "form-data") MultipartBody.Part image);

    @POST("auth/recoverPassword")
    Single<Response<BaseResponse>> recoverPassStep1(@Body RecoverPasswordStep1Request recoverPasswordStep1Request);

    @POST("auth/recoverPasswordFinal")
    Single<Response<BaseResponse>> recoverPassStep2(@Body RecoverPasswordStep2Request recoverPasswordStep1Request);

    @POST("auth/changePassword/{token}")
    Single<Response<BaseResponse>> changePassword(@Path("token") String token, @Body ChangePasswordRequest changePasswordRequest);

}
