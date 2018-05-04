package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.RankingService;
import com.thesis.smile.data.remote.services.TransactionsService;
import com.thesis.smile.domain.mapper.CurrentEnergyMapper;
import com.thesis.smile.domain.mapper.RankingHeaderMapper;
import com.thesis.smile.domain.mapper.TotalsMapper;
import com.thesis.smile.domain.mapper.TransactionMapper;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.RankingHeader;
import com.thesis.smile.domain.models.RankingHeaderTest;
import com.thesis.smile.domain.models.Totals;
import com.thesis.smile.domain.models.Transaction;

import org.threeten.bp.LocalDate;

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

    public Single<List<RankingHeaderTest>> getRankingRenewablesUsage(){
        String token = sharedPrefs.getUserToken();
        return rankingService.getRankingRenewablesUsage(token)
                .map(RankingHeaderMapper.INSTANCE::remoteToDomain);
    }

}
