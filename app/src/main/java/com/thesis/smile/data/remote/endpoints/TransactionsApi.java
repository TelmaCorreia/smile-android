package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.response.CurrentEnergyDataResponse;
import com.thesis.smile.data.remote.models.response.TotalsResponse;
import com.thesis.smile.data.remote.models.response.TransactionsResponse;

import org.threeten.bp.LocalDate;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransactionsApi {

    @GET("transactions/current/{token}")
    Single<Response<CurrentEnergyDataResponse>> getHomeData(@Path("token") String token);

    @GET("transactions/bought/{token}")
    Single<Response<TransactionsResponse>> getBoughtTransactions(@Path("token") String token, @Query("page") int page, @Query("size") int size);

    @GET("transactions/sold/{token}")
    Single<Response<TransactionsResponse>> getSoldTransactions(@Path("token") String token, @Query("page") int page, @Query("size") int size);

    @GET("transactions/all/{token}")
    Single<Response<TransactionsResponse>> getAllTransactions(@Path("token") String token, @Query("page") int page, @Query("size") int size);

    @GET("transactions/filter/all/{token}")
    Single<Response<TransactionsResponse>> getAllTransactionsFiltered(@Path("token") String token, @Query("initialdate")LocalDate initialDate, @Query("finaldate")LocalDate finalDate, @Query("page") int page, @Query("size") int size);

    @GET("transactions/filter/sell/{token}")
    Single<Response<TransactionsResponse>> getSellTransactionsFiltered(@Path("token") String token, @Query("initialdate")LocalDate initialDate, @Query("finaldate")LocalDate finalDate, @Query("page") int page, @Query("size") int size);

    @GET("transactions/filter/buy/{token}")
    Single<Response<TransactionsResponse>> getBuyTransactionsFiltered(@Path("token") String token, @Query("initialdate")LocalDate initialDate, @Query("finaldate")LocalDate finalDate, @Query("page") int page, @Query("size") int size);

    @GET("transactions/total/{token}")
    Single<Response<TotalsResponse>> getTotals(@Path("token") String token);


}
