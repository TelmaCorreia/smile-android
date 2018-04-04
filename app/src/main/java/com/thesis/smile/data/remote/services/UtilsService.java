package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.UtilsApi;
import com.thesis.smile.data.remote.models.ConfigsRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class UtilsService extends ApiService{

    private UtilsApi api;

    @Inject
    public UtilsService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(UtilsApi.class);
    }

    public Single<ConfigsRemote> getConfigs(){
        return api.getConfigs()
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }


}
