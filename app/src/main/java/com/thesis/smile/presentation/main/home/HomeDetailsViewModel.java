package com.thesis.smile.presentation.main.home;

import android.databinding.ObservableList;

import com.thesis.smile.BR;
import com.thesis.smile.R;
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

    }

    public void getTransactions(String type){
        Disposable disposable;
       if (type.equals(getResourceProvider().getString(R.string.details_bought_energy))){
           disposable = energyManager.getCurrentBoughtTransactions()
                   .compose(schedulersTransformSingleIo())
                   .subscribe(this::onTransactionReceived, this::onError);
       } else {
           disposable = energyManager.getCurrentSoldTransactions()
                   .compose(schedulersTransformSingleIo())
                   .subscribe(this::onTransactionReceived, this::onError);
       }

        addDisposable(disposable);

    }

    private void onTransactionReceived(List<Transaction> transactions) {
        for (Transaction t : transactions){
            if (type.equals(getResourceProvider().getString(R.string.details_bought_energy))){
                t.setType(getResourceProvider().getString(R.string.seller));
            }else{
                t.setType(getResourceProvider().getString(R.string.buyer));
            }
        }
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
