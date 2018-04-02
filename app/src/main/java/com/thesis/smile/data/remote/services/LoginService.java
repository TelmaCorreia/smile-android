package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.LoginApi;
import com.thesis.smile.data.remote.models.LoginRemote;
import com.thesis.smile.data.remote.models.request.LoginRequest;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiService;

import io.reactivex.Single;

public class LoginService extends ApiService{

    private LoginApi api;

    public Single<LoginRemote> login(String email, String password) {
        return api.login(new LoginRequest(email, password))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }
}
