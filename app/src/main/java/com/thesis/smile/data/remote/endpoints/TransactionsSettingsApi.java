package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.AddressRemote;
import com.thesis.smile.data.remote.models.BuySettingsRemote;
import com.thesis.smile.data.remote.models.NeighbourRemote;
import com.thesis.smile.data.remote.models.NeighboursRemote;
import com.thesis.smile.data.remote.models.SellSettingsRemote;
import com.thesis.smile.data.remote.models.TimeIntervalRemote;
import com.thesis.smile.data.remote.models.response.BuySettingsResponse;
import com.thesis.smile.data.remote.models.response.CurrentEnergyDataResponse;
import com.thesis.smile.data.remote.models.response.InfoPriceResponse;
import com.thesis.smile.data.remote.models.response.NeighboursResponse;
import com.thesis.smile.data.remote.models.response.SellSettingsResponse;
import com.thesis.smile.data.remote.models.response.TimeIntervalResponse;
import com.thesis.smile.data.remote.models.response.TimeIntervalsResponse;
import com.thesis.smile.data.remote.models.response.TransactionsResponse;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.domain.models.BuySettings;
import com.thesis.smile.domain.models.SellSettings;
import com.thesis.smile.domain.models.TimeInterval;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransactionsSettingsApi {

    @GET("transactions/settings/buy/{token}")
    Single<Response<BuySettingsResponse>> getBuySettings(@Path("token") String token);

    @PUT("transactions/settings/buy/{token}")
    Single<Response<BuySettingsResponse>> updateBuySettings(@Path("token") String userId, @Body BuySettingsRemote buySettingsRemote);

    @GET("transactions/settings/sell/{token}")
    Single<Response<SellSettingsResponse>> getSellSettings(@Path("token") String token);

    @PUT("transactions/settings/sell/{token}")
    Single<Response<SellSettingsResponse>> updateSellSettings(@Path("token") String userId, @Body SellSettingsRemote sellSettingsRemote);

    @GET("transactions/info_price/{token}")
    Single<Response<InfoPriceResponse>> getInfoPrice(@Path("token") String token);

    @GET("transactions/time_interval/sell/{token}")
    Single<Response<TimeIntervalsResponse>> getTimeIntervalsSell(@Path("token") String token);

    @GET("transactions/time_interval/buy/{token}")
    Single<Response<TimeIntervalsResponse>> getTimeIntervalsBuy(@Path("token") String token);

    @POST("transactions/time_interval/sell/{token}")
    Single<Response<TimeIntervalResponse>> postTimeIntervalSell(@Path("token") String userId, @Body TimeIntervalRemote timeIntervalRemote);

    @POST("transactions/time_interval/buy/{token}")
    Single<Response<TimeIntervalResponse>> postTimeIntervalBuy(@Path("token") String userId, @Body TimeIntervalRemote timeIntervalRemote);

    @PUT("transactions/time_interval/{token}")
    Single<Response<TimeIntervalResponse>> updateTimeInterval(@Path("token") String userId, @Body TimeIntervalRemote timeIntervalRemote);

    @HTTP(method = "DELETE", path = "transactions/time_interval/{token}", hasBody = true)
    Single<Response<BaseResponse>> deleteTimeInterval(@Path("token") String userId, @Body TimeIntervalRemote timeIntervalRemote);

    @GET("transactions/neighbours/buy/{token}")
    Single<Response<NeighboursResponse>> getNeighboursBuy(@Path("token") String token, @Query("page") int page, @Query("size") int size);

    @GET("transactions/neighbours/sell/{token}")
    Single<Response<NeighboursResponse>> getNeighboursSell(@Path("token") String token,  @Query("page") int page, @Query("size") int size);

    @PUT("transactions/neighbours/sell/{token}")
    Single<Response<BaseResponse>> updateNeighboursSell(@Path("token") String userId, @Body NeighboursRemote neighbours);

    @PUT("transactions/neighbours/buy/{token}")
    Single<Response<BaseResponse>> updateNeighboursBuy(@Path("token") String userId, @Body NeighboursRemote neighbours);


}
