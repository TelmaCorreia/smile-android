package com.thesis.smile.presentation.main.home;

import android.databinding.ObservableList;

import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;

import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class HomeDetailsViewModel extends BaseToolbarViewModel {

    private String type;
    List<Transaction> transactionList;
    private final ExclusiveObservableList<Transaction> transactions;

    private TransactionsManager transactionsManager;
    @Inject
    public HomeDetailsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                UiEvents uiEvents, TransactionsManager transactionsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.transactionsManager = transactionsManager;
        this.transactions= new ExclusiveObservableList<>();

    }

    public void getTransactions(String type){
        Disposable disposable;
       if (type.equals(getResourceProvider().getString(R.string.details_bought_energy))){
           disposable = transactionsManager.getCurrentBoughtTransactions(0,20)
                   .compose(schedulersTransformSingleIo())
                   .subscribe(this::onTransactionReceived, this::onError);
       } else {
           disposable = transactionsManager.getCurrentSoldTransactions(0,20)
                   .compose(schedulersTransformSingleIo())
                   .subscribe(this::onTransactionReceived, this::onError);
       }

        addDisposable(disposable);

    }

    private void onTransactionReceived(List<Transaction> transactions) {
        this.transactions.addAll(transactions);

    }

    public void setType(String type){
        this.type = type;
        getTransactions(type);
    }


    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }
}
