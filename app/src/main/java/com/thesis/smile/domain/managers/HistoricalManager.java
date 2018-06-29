package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.HistoricalService;
import com.thesis.smile.data.remote.services.RankingService;
import com.thesis.smile.domain.mapper.HistoricalDataMapper;
import com.thesis.smile.domain.mapper.RankingListMapper;
import com.thesis.smile.domain.models.HistoricalData;
import com.thesis.smile.domain.models.RankingModelList;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class HistoricalManager {

    private SharedPrefs sharedPrefs;
    private HistoricalService historicalService;

    @Inject
    public HistoricalManager(HistoricalService historicalService, SharedPrefs sharedPrefs) {
        this.historicalService = historicalService;
        this.sharedPrefs = sharedPrefs;
    }

    public Single<List<HistoricalData>> getDailyHistoricalData(LocalDate date){
        String token = sharedPrefs.getUserToken();
        return historicalService.getDailyHistoricalData(token, date)
                .map(HistoricalDataMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<HistoricalData>> getWeeklyHistoricalData(LocalDate date){
        String token = sharedPrefs.getUserToken();
        return historicalService.getWeeklyHistoricalData(token, date)
                .map(HistoricalDataMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<HistoricalData>> getMonthlyHistoricalData(LocalDate date){
        String token = sharedPrefs.getUserToken();
        return historicalService.getMonthlyHistoricalData(token, date)
                .map(HistoricalDataMapper.INSTANCE::remoteToDomain);
    }



}
