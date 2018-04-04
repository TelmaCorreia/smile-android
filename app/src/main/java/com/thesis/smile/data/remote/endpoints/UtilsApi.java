package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.ConfigsRemote;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.response.ConfigsResponse;
import com.thesis.smile.data.remote.models.response.UserResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UtilsApi {

    @GET("configs")
    Single<Response<ConfigsResponse>> getConfigs();

}
