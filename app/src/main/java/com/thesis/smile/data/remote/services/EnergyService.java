package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.EnergyApi;
import com.thesis.smile.data.remote.endpoints.UtilsApi;
import com.thesis.smile.data.remote.models.CurrentEnergyDataRemote;
import com.thesis.smile.data.remote.models.TransactionRemote;
import com.thesis.smile.data.remote.models.TransactionsRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;
import com.thesis.smile.domain.mapper.ConfigsMapper;
import com.thesis.smile.domain.mapper.CurrentEnergyMapper;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.CurrentEnergy;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class EnergyService extends ApiService{

    private EnergyApi api;

    @Inject
    public EnergyService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(EnergyApi.class);
    }

    public Single<CurrentEnergyDataRemote> getCurrentEnergyData(String token){
        return api.getCurrentEnergyData(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }


    public Single<List<TransactionRemote>> getCurrentBoughtTransactions(String token){
        return api.getCurrentBoughtTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }

    public Single<List<TransactionRemote>> getCurrentSoldTransactions(String token){
        return api.getCurrentSoldTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }

}
