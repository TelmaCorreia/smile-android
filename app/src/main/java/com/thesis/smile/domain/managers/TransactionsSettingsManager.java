package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.TransactionsService;
import com.thesis.smile.data.remote.services.TransactionsSettingsService;
import com.thesis.smile.domain.mapper.BuySettingsMapper;
import com.thesis.smile.domain.mapper.CurrentEnergyMapper;
import com.thesis.smile.domain.mapper.InfoPriceMapper;
import com.thesis.smile.domain.mapper.NeighbourMapper;
import com.thesis.smile.domain.mapper.SellSettingsMapper;
import com.thesis.smile.domain.mapper.TimeIntervalMapper;
import com.thesis.smile.domain.mapper.TotalsMapper;
import com.thesis.smile.domain.mapper.TransactionMapper;
import com.thesis.smile.domain.models.BuySettings;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.InfoPrice;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.SellSettings;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.domain.models.Totals;
import com.thesis.smile.domain.models.Transaction;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class TransactionsSettingsManager {

    private SharedPrefs sharedPrefs;
    private TransactionsSettingsService transactionsSettingsService;

    @Inject
    public TransactionsSettingsManager(TransactionsSettingsService transactionsSettingsService, SharedPrefs sharedPrefs) {
        this.transactionsSettingsService = transactionsSettingsService;
        this.sharedPrefs = sharedPrefs;
    }

    public Single<BuySettings> getBuySettings(){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.getBuySettings(token)
                .map(BuySettingsMapper.INSTANCE::remoteToDomain);
    }

    public Single<BuySettings> updateBuySettings(BuySettings buySettings){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.updateBuySettings(token, buySettings)
                .map(BuySettingsMapper.INSTANCE::remoteToDomain);
    }

    public Single<SellSettings> getSellSettings(){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.getSellSettings(token)
                .map(SellSettingsMapper.INSTANCE::remoteToDomain);
    }

    public Single<SellSettings> updateSellSettings(SellSettings sellSettings){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.updateSellSettings(token, sellSettings)
                .map(SellSettingsMapper.INSTANCE::remoteToDomain);
    }

    public Single<InfoPrice> getInfoPrice(){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.getInfoPrice(token)
                .map(InfoPriceMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<TimeInterval>> getTimeIntervalsBuy(){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.getTimeIntervalsBuy(token)
                .map(TimeIntervalMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<TimeInterval>> getTimeIntervalsSell(){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.getTimeIntervalsSell(token)
                .map(TimeIntervalMapper.INSTANCE::remoteToDomain);
    }

    public Single<TimeInterval> postTimeIntervalSell(TimeInterval timeInterval){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.postTimeIntervalSell(token, timeInterval)
                .map(TimeIntervalMapper.INSTANCE::remoteToDomain);
    }

    public Single<TimeInterval> postTimeIntervalBuy(TimeInterval timeInterval){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.postTimeIntervalBuy(token, timeInterval)
                .map(TimeIntervalMapper.INSTANCE::remoteToDomain);
    }

    public Single<TimeInterval> updateTimeInterval(TimeInterval timeInterval){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.updateTimeInterval(token, timeInterval)
                .map(TimeIntervalMapper.INSTANCE::remoteToDomain);
    }


    public Completable deleteTimeInterval(TimeInterval timeInterval){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.deleteTimeInterval(token, timeInterval);
    }


    public Single<List<Neighbour>> getNeighboursBuy(int page, int size){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.getNeighboursBuy(token, page, size)
                .map(NeighbourMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Neighbour>> getNeighboursSell(int page, int size){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.getNeighboursSell(token, page, size)
                .map(NeighbourMapper.INSTANCE::remoteToDomain);
    }

    public Single<String> updateNeighboursSell(List<Neighbour> list){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.updateNeighboursSell(token, list);
    }

    public Single<String> updateNeighboursBuy(List<Neighbour> list){
        String token = sharedPrefs.getUserToken();
        return transactionsSettingsService.updateNeighboursBuy(token, list);
    }
}
