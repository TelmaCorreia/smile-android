package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.UserApi;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;
import com.thesis.smile.domain.mapper.UserMapper;
import com.thesis.smile.domain.models.User;

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

    public Single<User> getUserWithId(String userId){
        return api.userWithId(userId)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(UserMapper.INSTANCE::remoteToDomain);
    }

    public Single<User> updateUserWithId(String userId, UserRemote user){
        return api.updateUserWithId(userId, user)
                .compose(networkMapTransform())
                .map(BaseResponse::getData)
                .map(UserMapper.INSTANCE::remoteToDomain);
    }
}
