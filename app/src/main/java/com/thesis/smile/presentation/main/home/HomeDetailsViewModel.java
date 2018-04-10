package com.thesis.smile.presentation.main.home;

import android.databinding.ObservableList;

import com.thesis.smile.domain.managers.EnergyManager;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;

import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class HomeDetailsViewModel extends BaseToolbarViewModel {

    private String type;
    List<Transaction> transactionList;
    private final ExclusiveObservableList<Transaction> transactions;

    private EnergyManager energyManager;
    @Inject
    public HomeDetailsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                UiEvents uiEvents, EnergyManager energyManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.energyManager = energyManager;
        this.transactions= new ExclusiveObservableList<>();

      Disposable disposable = energyManager.getCurrentBoughtTransactions()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onTransactionReceived, this::onError);

      addDisposable(disposable);
    }

    private void onTransactionReceived(List<Transaction> transactions) {
        transactions.addAll(transactions);
    }

    public void setType(String type){
        this.type = type;
    }


    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }
}
