package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.UserApi;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.UsersListRemote;
import com.thesis.smile.data.remote.models.UsersRemote;
import com.thesis.smile.data.remote.models.response.UsersResponse;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;
import com.thesis.smile.domain.mapper.EnergyParamsMapper;
import com.thesis.smile.domain.mapper.UserMapper;
import com.thesis.smile.domain.mapper.UsersMapper;
import com.thesis.smile.domain.models.EnergyParams;
import com.thesis.smile.domain.models.User;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public Single<String> getAccountSeed(String id){
        return api.getSeed(id)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<UserRemote> updateUserWithToken(String token, User user){
        return api.updateUserWithToken(token, UserMapper.INSTANCE.domainToRemote(user))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<UserRemote> updateFirebaseToken(String token, String firebaseToken){
        return api.updateFirebaseToken(token, firebaseToken)
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<UserRemote> updateIotaAddress(String token, String iotaAddress){
        return api.updateIotaAddress(token, iotaAddress)
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<UserRemote> updateEnergyParamsWithToken(String token, User user){
        return api.updateEnergyParams(token, UserMapper.INSTANCE.domainToRemote(user))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<UserRemote> updateUserProfilePic(String currentUserId, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        return api.updateUserImage(currentUserId, imagePart)
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<List<UsersRemote>> getUsers(String token){
        return api.getUsers(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(UsersListRemote::getUsers);
    }
}
