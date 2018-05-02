package com.thesis.smile.data.remote.services;

import com.thesis.smile.data.remote.endpoints.TransactionsApi;
import com.thesis.smile.data.remote.models.CurrentEnergyDataRemote;
import com.thesis.smile.data.remote.models.TotalsRemote;
import com.thesis.smile.data.remote.models.TransactionRemote;
import com.thesis.smile.data.remote.models.TransactionsRemote;
import com.thesis.smile.data.remote.models.response.base.BaseResponse;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.services.base.ApiService;

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

    public Single<CurrentEnergyDataRemote> getCurrentEnergyData(String token){
        return api.getCurrentEnergyData(token)
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

}
