package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.HistoricalApi;
import com.thesis.smile.data.remote.models.HistoricalDataObjectRemote;
import com.thesis.smile.data.remote.models.HistoricalDataRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

import retrofit2.Retrofit;

public class HistoricalService extends ApiService{

    private HistoricalApi api;

    @Inject
    public HistoricalService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(HistoricalApi.class);
    }

    public Single<List<HistoricalDataRemote>> getDailyHistoricalData(String userId, LocalDate date){
        return api.getDailyHistoricalData(userId, date)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(HistoricalDataObjectRemote::getData);
    }

    public Single<List<HistoricalDataRemote>> getWeeklyHistoricalData(String userId, LocalDate date){
        return api.getWeeklyHistoricalData(userId, date)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(HistoricalDataObjectRemote::getData);
    }

    public Single<List<HistoricalDataRemote>> getMonthlyHistoricalData(String userId, LocalDate date){
        return api.getMonthlyHistoricalData(userId, date)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(HistoricalDataObjectRemote::getData);
    }


}
