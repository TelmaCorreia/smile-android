package com.thesis.smile.data.remote.endpoints;

import com.thesis.smile.data.remote.models.response.RankingRenewablesResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RankingApi {

    @GET("ranking/renewable/{token}")
    Single<Response<RankingRenewablesResponse>> getRenewableEnergyUsageRanking(@Path("token") String token);

  }
