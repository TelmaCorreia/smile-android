package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.RankingApi;
import com.thesis.smile.data.remote.models.RankingRemote;
import com.thesis.smile.data.remote.models.RankingRemoteList;
import com.thesis.smile.data.remote.models.RankingRenewableRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class RankingService extends ApiService{

    private RankingApi api;

    @Inject
    public RankingService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(RankingApi.class);
    }


    public Single<List<RankingRemoteList>> getRankingRenewablesUsage(String token){
        return api.getRenewableEnergyUsageRanking(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(RankingRenewableRemote::getRankings);
    }






}
