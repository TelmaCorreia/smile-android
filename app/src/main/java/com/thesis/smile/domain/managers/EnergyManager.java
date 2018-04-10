package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.models.TransactionRemote;
import com.thesis.smile.data.remote.services.EnergyService;
import com.thesis.smile.data.remote.services.UserService;
import com.thesis.smile.domain.mapper.CurrentEnergyMapper;
import com.thesis.smile.domain.mapper.TransactionMapper;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.Transaction;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class EnergyManager {

    private SharedPrefs sharedPrefs;
    private EnergyService energyService;

    @Inject
    public EnergyManager(EnergyService energyService, SharedPrefs sharedPrefs) {
        this.energyService = energyService;
        this.sharedPrefs = sharedPrefs;
    }

    public Single<CurrentEnergy> getCurrentEnergyData(){
        String token = sharedPrefs.getUserToken();
        return energyService.getCurrentEnergyData(token)
                .map(CurrentEnergyMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Transaction>> getCurrentBoughtTransactions(){
        String token = sharedPrefs.getUserToken();
        return energyService.getCurrentBoughtTransactions(token)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Transaction>> getCurrentSoldTransactions(){
        String token = sharedPrefs.getUserToken();
        return energyService.getCurrentSoldTransactions(token)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }
}
