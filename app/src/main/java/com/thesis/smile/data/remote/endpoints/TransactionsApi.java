package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.AddressRemote;
import com.thesis.smile.data.remote.models.TransactionRemote;
import com.thesis.smile.data.remote.models.response.CurrentEnergyDataResponse;
import com.thesis.smile.data.remote.models.response.TotalsResponse;
import com.thesis.smile.data.remote.models.response.TransactionsResponse;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;

import org.threeten.bp.LocalDate;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("transactions/total/daily/{token}")
    Single<Response<TotalsResponse>> getDailyTotals(@Path("token") String token);

    @GET("transactions/total/weekly/{token}")
    Single<Response<TotalsResponse>> getWeeklyTotals(@Path("token") String token);

    @GET("transactions/total/monthly/{token}")
    Single<Response<TotalsResponse>> getMonthlyTotals(@Path("token") String token);

    @POST("transactions/address/{token}")
    Single<Response<BaseResponse>> postAddress(@Path("token") String userId, @Body AddressRemote addressRemote);

    @PUT("transactions/address/{token}")
    Single<Response<BaseResponse>> updateAddressBundle(@Path("token") String userId, @Body AddressRemote addressRemote);

    @PUT("transactions/{token}")
    Single<Response<BaseResponse>> updateTransaction(@Path("token") String userId, @Body TransactionRemote addressRemote);

    @GET("transactions/pay/{token}")
    Single<Response<TransactionsResponse>> getTransactionsToPay(@Path("token") String token);

    @GET("transactions/totals/validated/{token}")
    Single<Response<TotalsResponse>> getTotalsValidatedTransactions(@Path("token") String token);

    @GET("transactions/totals/attached/{token}")
    Single<Response<TotalsResponse>> getTotalsAttachedTransactions(@Path("token") String token);

    @GET("transactions/totals/nonattached/{token}")
    Single<Response<TotalsResponse>> getTotalsNonAttachedTransactions(@Path("token") String token);

    @GET("transactions/totals/validatedattached/{token}")
    Single<Response<TotalsResponse>> getTotalsValidatedAndAttachedTransactions(@Path("token") String token);


}
