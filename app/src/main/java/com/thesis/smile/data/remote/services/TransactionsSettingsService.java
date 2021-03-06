package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.TransactionsSettingsApi;
import com.thesis.smile.data.remote.models.AddressRemote;
import com.thesis.smile.data.remote.models.BuySettingsRemote;
import com.thesis.smile.data.remote.models.InfoPriceRemote;
import com.thesis.smile.data.remote.models.NeighbourRemote;
import com.thesis.smile.data.remote.models.NeighboursRemote;
import com.thesis.smile.data.remote.models.SellSettingsRemote;
import com.thesis.smile.data.remote.models.TimeIntervalRemote;
import com.thesis.smile.data.remote.models.TimeIntervalsRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;
import com.thesis.smile.domain.mapper.AddressMapper;
import com.thesis.smile.domain.mapper.BuySettingsMapper;
import com.thesis.smile.domain.mapper.NeighbourMapper;
import com.thesis.smile.domain.mapper.SellSettingsMapper;
import com.thesis.smile.domain.mapper.TimeIntervalMapper;
import com.thesis.smile.domain.models.Address;
import com.thesis.smile.domain.models.BuySettings;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.SellSettings;
import com.thesis.smile.domain.models.TimeInterval;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class TransactionsSettingsService extends ApiService {

    private TransactionsSettingsApi api;

    @Inject
    public TransactionsSettingsService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(TransactionsSettingsApi.class);
    }

    public Single<BuySettingsRemote> getBuySettings(String token){
        return api.getBuySettings(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<BuySettingsRemote> updateBuySettings(String token, BuySettings buySettings){
        return api.updateBuySettings(token, BuySettingsMapper.INSTANCE.domainToRemote(buySettings))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<SellSettingsRemote> getSellSettings(String token){
        return api.getSellSettings(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<SellSettingsRemote> updateSellSettings(String token, SellSettings sellSettings){
        return api.updateSellSettings(token, SellSettingsMapper.INSTANCE.domainToRemote(sellSettings))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<InfoPriceRemote> getInfoPrice(String token){
        return api.getInfoPrice(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<List<TimeIntervalRemote>> getTimeIntervalsBuy(String token){
        return api.getTimeIntervalsBuy(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TimeIntervalsRemote::getTimeIntervals);
    }

    public Single<List<TimeIntervalRemote>> getTimeIntervalsSell(String token){
        return api.getTimeIntervalsSell(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TimeIntervalsRemote::getTimeIntervals);
    }

    public Single<TimeIntervalRemote> postTimeIntervalSell(String token, TimeInterval timeInterval){
        return api.postTimeIntervalSell(token, TimeIntervalMapper.INSTANCE.domainToRemote(timeInterval))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<TimeIntervalRemote> postTimeIntervalBuy(String token, TimeInterval timeInterval){
        return api.postTimeIntervalBuy(token,  TimeIntervalMapper.INSTANCE.domainToRemote(timeInterval))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Single<TimeIntervalRemote> updateTimeInterval(String token, TimeInterval timeInterval){
        return api.updateTimeInterval(token,  TimeIntervalMapper.INSTANCE.domainToRemote(timeInterval))
                .compose(networkMapTransform())
                .map(BaseResponse::getData);
    }

    public Completable deleteTimeInterval(String token, TimeInterval timeInterval){
        return api.deleteTimeInterval(token,  TimeIntervalMapper.INSTANCE.domainToRemote(timeInterval))
                .compose(networkMapTransform())
                .toCompletable();
    }

    public Single<List<NeighbourRemote>> getNeighboursBuy(String token, int page, int size){
        return api.getNeighboursBuy(token, page,size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(NeighboursRemote::getNeighbours);
    }

    public Single<List<NeighbourRemote>> getNeighboursSell(String token, int page, int size){
        return api.getNeighboursSell(token, page,size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(NeighboursRemote::getNeighbours);
    }

    public Single<String> updateNeighboursSell(String token,  List<Neighbour> neighbours){
        return api.updateNeighboursSell(token, new NeighboursRemote(NeighbourMapper.INSTANCE.domainToRemote(neighbours)))
                .compose(networkMapTransform())
                .map(BaseResponse::getCode);
    }

    public  Single<String> updateNeighboursBuy(String token, List<Neighbour> neighbours){
        return api.updateNeighboursBuy(token, new NeighboursRemote(NeighbourMapper.INSTANCE.domainToRemote(neighbours)))
                .compose(networkMapTransform())
                .map(BaseResponse::getCode);
    }

}
