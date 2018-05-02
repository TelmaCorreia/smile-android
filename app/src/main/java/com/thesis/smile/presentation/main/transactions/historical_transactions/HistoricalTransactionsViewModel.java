package com.thesis.smile.presentation.main.transactions.historical_transactions;

import android.databinding.Bindable;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.exceptions.http.GenericErrorException;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.models.Totals;
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
    private TransactionsManager transactionsManager;
    private String type;
    private String timePeriod;
    private String initialDate;
    private String finalDate;
    private Totals totals;

    @Inject
    public HistoricalTransactionsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsManager transactionsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.transactionsManager = transactionsManager;
        transactions = new ExclusiveObservableList<>();
        getTotals();
    }


    @Bindable
    public String getSales() {
        if (totals!=null){
            return String.format("%.2f", totals.getTotalSold()) + getResourceProvider().getString(R.string.coin);
        }
        return null;
    }


    @Bindable
    public String getPurchases() {
        if(totals!=null){
            return String.format("%.2f", totals.getTotalBought()) + getResourceProvider().getString(R.string.coin);
        }
        return null;
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
            disposable = transactionsManager.getBoughtTransactions(0, 20)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else if (type.equals(getResourceProvider().getString(R.string.details_sold_energy))){
            disposable = transactionsManager.getSoldTransactions(0, 20)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else{
           disposable = transactionsManager.getAllTransactions(0,20)
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

    public void getTotals() {
        Disposable disposable = transactionsManager.getTotals()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onTotalsReceived, this::onError);
        addDisposable(disposable);
    }

    private void onTotalsReceived(Totals totals) {
        this.totals = totals;
        notifyPropertyChanged(BR.purchases);
        notifyPropertyChanged(BR.sales);
    }
    
}
