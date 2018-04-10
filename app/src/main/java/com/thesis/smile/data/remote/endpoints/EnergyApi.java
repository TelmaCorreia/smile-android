package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.TransactionsRemote;
import com.thesis.smile.data.remote.models.response.CurrentEnergyDataResponse;
import com.thesis.smile.data.remote.models.response.TransactionsResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EnergyApi {

    @GET("energy/current/{token}")
    Single<Response<CurrentEnergyDataResponse>> getCurrentEnergyData(@Path("token") String token);

    @GET("energy/bough/{token}")
    Single<Response<TransactionsResponse>> getCurrentBoughtTransactions(@Path("token") String token);

    @GET("energy/sold/{token}")
    Single<Response<TransactionsResponse>> getCurrentSoldTransactions(@Path("token") String token);


}
