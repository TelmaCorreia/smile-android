package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.EnergyParamsRemote;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.response.UserResponse;
import com.thesis.smile.domain.models.EnergyParams;
import com.thesis.smile.domain.models.User;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {

    @GET("users/{token}")
    Single<Response<UserResponse>> userWithId(@Path("token") String userId);

    @PUT("users/{token}")
    Single<Response<UserResponse>> updateUserWithToken(@Path("token") String userId, @Body UserRemote user);

    @PUT("users/{token}/energyParams")
    Single<Response<UserResponse>> updateEnergyParams(@Path("token") String userId, @Body UserRemote user);

    @Multipart
    @PUT("users/{token}/image")
    Single<Response<UserResponse>> updateUserImage(@Path("token") String userId, @Part(encoding = "form-data") MultipartBody.Part image);

}
