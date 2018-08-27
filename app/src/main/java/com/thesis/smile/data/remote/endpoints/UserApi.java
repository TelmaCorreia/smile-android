package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.response.UserResponse;
import com.thesis.smile.data.remote.models.response.UsersResponse;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;

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

    @GET("users/seed/")
    Single<Response<BaseResponse<String>>> getSeed();

    @PUT("users/{token}")
    Single<Response<UserResponse>> updateUserWithToken(@Path("token") String userId, @Body UserRemote user);

    @PUT("users/firebase/{token}/{firebaseToken}")
    Single<Response<UserResponse>> updateFirebaseToken(@Path("token") String userId, @Path("firebaseToken") String firebaseToken);

    @PUT("users/iotaAddress/{token}/{address}")
    Single<Response<UserResponse>> updateIotaAddress(@Path("token") String userId, @Path("address") String iotaAddress);

    @PUT("users/{token}/energyParams")
    Single<Response<UserResponse>> updateEnergyParams(@Path("token") String userId, @Body UserRemote user);

    @Multipart
    @PUT("users/{token}/image")
    Single<Response<UserResponse>> updateUserImage(@Path("token") String userId, @Part(encoding = "form-data") MultipartBody.Part image);

    @GET("users/root/{token}")
    Single<Response<UsersResponse>> getUsers(@Path("token") String userId);
}
