package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.response.HistoricalDataResponse;
import com.thesis.smile.data.remote.models.response.UserResponse;

import org.threeten.bp.LocalDate;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoricalApi {

    @GET("historical/daily/{token}")
    Single<Response<HistoricalDataResponse>> getDailyHistoricalData(@Path("token") String userId, @Query("initialdate")LocalDate initialDate);

    @GET("historical/weekly/{token}")
    Single<Response<HistoricalDataResponse>> getWeeklyHistoricalData(@Path("token") String userId, @Query("initialdate")LocalDate initialDate);

    @GET("historical/monthly/{token}")
    Single<Response<HistoricalDataResponse>> getMonthlyHistoricalData(@Path("token") String userId, @Query("initialdate")LocalDate initialDate);


}
