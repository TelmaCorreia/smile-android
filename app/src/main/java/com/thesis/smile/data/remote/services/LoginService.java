package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.LoginApi;
import com.thesis.smile.data.remote.models.LoginRemote;
import com.thesis.smile.data.remote.models.request.LoginRequest;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class LoginService extends ApiService{

    private LoginApi api;

    @Inject
    public LoginService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(LoginApi.class);
    }

    public Single<LoginRemote> login(String email, String password) {
        return api.login(new LoginRequest(email, password))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }
}
