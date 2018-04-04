package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.LoginApi;
import com.thesis.smile.data.remote.endpoints.UserApi;
import com.thesis.smile.data.remote.models.EnergyParams;
import com.thesis.smile.data.remote.models.LoginRemote;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.request.LoginRequest;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class UserService extends ApiService{

    private UserApi api;

    @Inject
    public UserService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(UserApi.class);
    }

    public Single<UserRemote> getUserWithId(String userId){
        return api.userWithId(userId)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<UserRemote> updateUserWithId(String userId, UserRemote user){
        return api.updateUserWithId(userId, user)
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }
}
