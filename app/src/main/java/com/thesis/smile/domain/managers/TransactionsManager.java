package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.TransactionsService;
import com.thesis.smile.domain.mapper.CurrentEnergyMapper;
import com.thesis.smile.domain.mapper.TotalsMapper;
import com.thesis.smile.domain.mapper.TransactionMapper;
import com.thesis.smile.domain.models.Address;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.Neighbour;
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

    public Single<Totals> getDailyTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getDailyTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getWeeklyTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getWeeklyTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getMonthlyTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getMonthlyTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<String> updateAddressBundle(Address address){
        String token = sharedPrefs.getUserToken();
        return transactionsService.updateAddressBundle(token,address );
    }

    public Single<String> updateTransaction(Transaction transaction){
        String token = sharedPrefs.getUserToken();
        return transactionsService.updateTransaction(token,transaction );
    }

    public Single<String> insertAddress(Address address){
        String token = sharedPrefs.getUserToken();
        return transactionsService.postAddress(token,address );
    }

    public Single<List<Transaction>> getTransactionsToPay(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getTransactionsToPay(token)
                .map(TransactionMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getValidatedTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getValidatedTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getAttachedTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getAttachedTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getValidatedAndAttachedDailyTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getValidatedAndAttachedDailyTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getValidatedAndAttachedWeeklyTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getValidatedAndAttachedWeeklyTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getValidatedAndAttachedMonthlyTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getValidatedAndAttachedMonthlyTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getValidatedAndAttachedTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getValidatedAndAttachedTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<Totals> getNonAttachedTotals(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getNonAttachedTotals(token)
                .map(TotalsMapper.INSTANCE::remoteToDomain);
    }

    public Single<String> getAddressesQuantity(){
        String token = sharedPrefs.getUserToken();
        return transactionsService.getAddressesQuantity(token);
    }
}
