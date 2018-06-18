package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.RankingService;
import com.thesis.smile.domain.mapper.RankingListMapper;
import com.thesis.smile.domain.mapper.RankingMapper;
import com.thesis.smile.domain.models.Ranking;
import com.thesis.smile.domain.models.RankingModelList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RankingsManager {

    private SharedPrefs sharedPrefs;
    private RankingService rankingService;

    @Inject
    public RankingsManager(RankingService rankingService, SharedPrefs sharedPrefs) {
        this.rankingService = rankingService;
        this.sharedPrefs = sharedPrefs;
    }

    public Single<List<RankingModelList>> getRankingRenewablesUsage(){
        String token = sharedPrefs.getUserToken();
        return rankingService.getRankingRenewablesUsage(token)
                .map(RankingListMapper.INSTANCE::remoteToDomain);
    }

}
