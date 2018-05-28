package com.thesis.smile.presentation.main.home;

import android.databinding.Bindable;
import android.databinding.ObservableList;
import android.view.View;

import com.google.common.collect.Maps;
import com.thesis.smile.BR;
import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;

import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class HomeDetailsViewModel extends BaseToolbarViewModel {

    private String type;
    private boolean lastPage;
    private boolean loading;
    private Map<String, Transaction> transactionMap;
    private final ExclusiveObservableList<Transaction> transactions;

    private TransactionsManager transactionsManager;
    @Inject
    public HomeDetailsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                UiEvents uiEvents, TransactionsManager transactionsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.transactionsManager = transactionsManager;
        this.transactions= new ExclusiveObservableList<>();
        this.transactionMap = new HashMap<>();

    }

    public void getTransactions(String type){
        Disposable disposable;
        setLoading(true);
       if (type.equals(getResourceProvider().getString(R.string.details_bought_energy))){
           disposable = transactionsManager.getCurrentBoughtTransactions(getPage(), Constants.PAGE_SIZE)
                   .compose(schedulersTransformSingleIo())
                   .subscribe(this::onTransactionReceived, this::onError);
       } else {
           disposable = transactionsManager.getCurrentSoldTransactions(getPage(),Constants.PAGE_SIZE)
                   .compose(schedulersTransformSingleIo())
                   .subscribe(this::onTransactionReceived, this::onError);
       }

        addDisposable(disposable);

    }

    private void onTransactionReceived(List<Transaction> transactions) {
        int size = transactionMap.size();
        this.transactionMap = Maps.uniqueIndex(transactions, t -> t.getId());
        if (transactions.size()!= size){ this.transactions.addAll(transactions);}
        this.lastPage= transactions.isEmpty();
        setLoading(false);

    }

    public void setLoading(boolean loading){
        this.loading=loading;
        notifyPropertyChanged(BR.progressBarVisible);
    }

    public int getPage(){
        return this.transactions.size();
    }
    
    public void setType(String type){
        this.type = type;
        getTransactions(type);
    }


    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public String getType() {
        return type;
    }

    @Bindable
    public int getProgressBarVisible() {
        return isLoading()? View.VISIBLE:View.GONE;
    }
}
