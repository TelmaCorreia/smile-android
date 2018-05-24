package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.TransactionsService;
import com.thesis.smile.domain.mapper.CurrentEnergyMapper;
import com.thesis.smile.domain.mapper.TotalsMapper;
import com.thesis.smile.domain.mapper.TransactionMapper;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.Totals;
import com.thesis.smile.domain.models.Transaction;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class TransactionsManager {

    private SharedPrefs sharedPrefs;
    private TransactionsService transactionsService;

    @Inject
    public TransactionsManager(TransactionsService transactionsService, SharedPrefs sharedPrefs) {
        this.transactionsService = transactionsService;
        this.sharedPrefs = sharedPrefs;
    }

    public Single<CurrentEnergy> getHomeData(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getHomeData(token)
                .map(CurrentEnergyMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Transaction>> getCurrentBoughtTransactions(int page, int size){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getBuyTransactionsFiltered(token, page,size, LocalDate.now(), LocalDate.now() )
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Transaction>> getCurrentSoldTransactions(int page, int size){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getSellTransactionsFiltered(token, page,size, LocalDate.now(), LocalDate.now())
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Transaction>> getBoughtTransactions(int page, int size){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getBoughtTransactions(token, page, size)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Transaction>> getSoldTransactions(int page, int size){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getSoldTransactions(token, page, size)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Observable<List<Transaction>> getAllTransactions(int page, int size, LocalDate initialDate, LocalDate finalDate){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getAllTransactionsFiltered(token, page, size, initialDate, finalDate)
                .map(TransactionMapper.INSTANCE::remoteToDomain).toObservable();

    }
    public Single<List<Transaction>> getAllTransactionsFiltered(int page, int size, LocalDate initialDate, LocalDate finalDate){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getAllTransactionsFiltered(token, page, size, initialDate, finalDate)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Single<List<Transaction>> getSellTransactionsFiltered(int page, int size, LocalDate initialDate, LocalDate finalDate){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getSellTransactionsFiltered(token, page, size, initialDate, finalDate)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }
    public Single<List<Transaction>> getBuyTransactionsFiltered(int page, int size, LocalDate initialDate, LocalDate finalDate){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getBuyTransactionsFiltered(token, page, size, initialDate, finalDate)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }
}
