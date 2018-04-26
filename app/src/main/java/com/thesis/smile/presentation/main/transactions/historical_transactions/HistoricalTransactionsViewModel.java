package com.thesis.smile.presentation.main.transactions.historical_transactions;

import android.databinding.Bindable;
import android.databinding.ObservableList;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.EnergyManager;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HistoricalTransactionsViewModel extends BaseViewModel {

    private final ExclusiveObservableList<Transaction> transactions;
    private PublishRelay<DialogEvent> openInitalDateCalendarObservable = PublishRelay.create();
    private PublishRelay<DialogEvent> openFinalDateCalendarObservable = PublishRelay.create();
    private EnergyManager energyManager;
    private String type;
    private String timePeriod;
    private String initialDate;
    private String finalDate;

    @Inject
    public HistoricalTransactionsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, EnergyManager energyManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.energyManager = energyManager;
        transactions = new ExclusiveObservableList<>();
    }


    @Bindable
    public String getSales() {
        return "0.32 €";
    }


    @Bindable
    public String getPurchases() {
        return "0.34 €";
    }

    @Bindable
    public String getInitialDate() {
        return this.initialDate;
    }

    public void setInitialDate(String date) {
        this.initialDate = date;
        notifyPropertyChanged(BR.initialDate);
    }

    @Bindable
    public String getFinalDate() {
        return this.finalDate;
    }

    public void setFinalDate(String date) {
        this.finalDate = date;
        notifyPropertyChanged(BR.finalDate);
    }


    @Bindable
    public int getSetPeriodVisible() {
        return (timePeriod != null && timePeriod.equals(getResourceProvider().getString(R.string.transactions_specific_period))) ? View.VISIBLE : View.GONE;
    }

    public void onInitialDateClick() {
        openInitalDateCalendarObservable.accept(new DialogEvent());
    }

    public void onFinalDateClick() {
        openFinalDateCalendarObservable.accept(new DialogEvent());
    }


    public void getTransactions(String type) {
        Disposable disposable;
        if (type.equals(getResourceProvider().getString(R.string.details_bought_energy))) {
            disposable = energyManager.getCurrentBoughtTransactions()
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else if (type.equals(getResourceProvider().getString(R.string.details_sold_energy))){
            disposable = energyManager.getCurrentSoldTransactions()
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else{
            //FIXME
           disposable = energyManager.getCurrentBoughtTransactions()
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        }

        addDisposable(disposable);



    }

    private void onTransactionReceived(List<Transaction> transactions) {
        for (Transaction t : transactions){
            if (type.equals(getResourceProvider().getString(R.string.details_bought_energy))){
                t.setType(getResourceProvider().getString(R.string.transactions_buy));
            }else{
                t.setType(getResourceProvider().getString(R.string.transactions_sell));
            }
        }
       // this.transactions.removeAll(this.transactions);
        this.transactions.addAll(transactions);

    }

    public void setType(String type){
        this.type = type;
        getTransactions(type);
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
        notifyPropertyChanged(BR.setPeriodVisible);
    }

    Observable<DialogEvent> observeInitialDateCalendarDialog(){
        return openInitalDateCalendarObservable;
    }

    Observable<DialogEvent> observeFinalDateCalendarDialog(){
        return openFinalDateCalendarObservable;
    }
}
