package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.TransactionsApi;
import com.thesis.smile.data.remote.models.CurrentEnergyDataRemote;
import com.thesis.smile.data.remote.models.TotalsRemote;
import com.thesis.smile.data.remote.models.TransactionRemote;
import com.thesis.smile.data.remote.models.TransactionsRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;
import com.thesis.smile.domain.mapper.AddressMapper;
import com.thesis.smile.domain.mapper.TransactionMapper;
import com.thesis.smile.domain.models.Address;
import com.thesis.smile.domain.models.Transaction;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class TransactionsService extends ApiService{

    private TransactionsApi api;

    @Inject
    public TransactionsService(Retrofit retrofit, ApiError apiError){
        super(retrofit, apiError);
        this.api = retrofit.create(TransactionsApi.class);
    }

    public Single<CurrentEnergyDataRemote> getHomeData(String token){
        return api.getHomeData(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<List<TransactionRemote>> getBoughtTransactions(String token, int page, int size){
        return api.getBoughtTransactions(token, page,size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }

    public Single<List<TransactionRemote>> getSoldTransactions(String token, int page, int size){
        return api.getSoldTransactions(token, page, size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }

    public Single<List<TransactionRemote>> getAllTransactions(String token, int page, int size){
        return api.getAllTransactions(token, page, size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }
    public Single<List<TransactionRemote>> getAllTransactionsFiltered(String token, int page, int size, LocalDate initialDate, LocalDate finalDate){
        return api.getAllTransactionsFiltered(token, initialDate, finalDate, page, size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }
    public Single<List<TransactionRemote>> getSellTransactionsFiltered(String token, int page, int size, LocalDate initialDate, LocalDate finalDate){
        return api.getSellTransactionsFiltered(token, initialDate, finalDate, page, size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }
    public Single<List<TransactionRemote>> getBuyTransactionsFiltered(String token, int page, int size, LocalDate initialDate, LocalDate finalDate){
        return api.getBuyTransactionsFiltered(token, initialDate, finalDate, page, size)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }

    public Single<TotalsRemote> getTotals(String token){
        return api.getTotals(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getDailyTotals(String token){
        return api.getDailyTotals(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getWeeklyTotals(String token){
        return api.getWeeklyTotals(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getMonthlyTotals(String token){
        return api.getMonthlyTotals(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public  Single<String> updateAddressBundle(String token, Address address){
        return api.updateAddressBundle(token, AddressMapper.INSTANCE.domainToRemote(address))
                .compose(networkMapTransform())
                .map(BaseResponse::getCode);
    }
    public  Single<String> updateTransaction(String token, Transaction transaction){
        return api.updateTransaction(token, TransactionMapper.INSTANCE.domainToRemote(transaction))
                .compose(networkMapTransform())
                .map(BaseResponse::getCode);
    }

    public  Single<String> postAddress(String token, Address address){
        return api.postAddress(token, AddressMapper.INSTANCE.domainToRemote(address))
                .compose(networkMapTransform())
                .map(BaseResponse::getCode);
    }

    public Single<List<TransactionRemote>> getTransactionsToPay(String token){
        return api.getTransactionsToPay(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData)
                .map(TransactionsRemote::getTransactions);
    }

    public Single<TotalsRemote> getValidatedTotals(String token){
        return api.getTotalsValidatedTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getAttachedTotals(String token){
        return api.getTotalsAttachedTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getNonAttachedTotals(String token){
        return api.getTotalsNonAttachedTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getValidatedAndAttachedTotals(String token){
        return api.getTotalsValidatedAndAttachedTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getValidatedAndAttachedDailyTotals(String token){
        return api.getTotalsValidatedAndAttachedDailyTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getValidatedAndAttachedWeeklyTotals(String token){
        return api.getTotalsValidatedAndAttachedWeeklyTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }

    public Single<TotalsRemote> getValidatedAndAttachedMonthlyTotals(String token){
        return api.getTotalsValidatedAndAttachedMonthlyTransactions(token)
                .compose(networkMapTransform())
                .onErrorResumeNext(Single::error)
                .map(BaseResponse::getData);
    }
}
