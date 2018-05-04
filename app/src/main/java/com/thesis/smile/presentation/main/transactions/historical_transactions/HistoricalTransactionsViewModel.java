package com.thesis.smile.presentation.main.transactions.historical_transactions;

import android.databinding.Bindable;
import android.databinding.ObservableList;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.models.Totals;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalDate;

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
    private LocalDate fromDate;
    private LocalDate toDate;
    private Totals totals;

    @Inject
    public HistoricalTransactionsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsManager transactionsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.transactionsManager = transactionsManager;
        transactions = new ExclusiveObservableList<>();
        initDates();
        getTotals();
    }

    private void initDates() {
        LocalDate now = LocalDate.now();
        this.fromDate = now.minusDays(30);
        this.toDate= now;
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
        if (timePeriod != null && timePeriod.equals(getResourceProvider().getString(R.string.transactions_specific_period))){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public void onInitialDateClick() {
        openInitalDateCalendarObservable.accept(new DialogEvent());
    }

    public void onFinalDateClick() {
        openFinalDateCalendarObservable.accept(new DialogEvent());
    }


  public String getType(){
        return type;
  }

    public void setType(String type){
        this.type = type;
        getTransactions(type);
        notifyPropertyChanged(BR.setPeriodVisible);
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
        notifyPropertyChanged(BR.setPeriodVisible);
        initDates();
        getTransactions(getType());
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

    public void getTransactionsFiltered() {
        if (fromDate!=null && toDate!=null && fromDate.isAfter(toDate)){
            getUiEvents().showToast("O período seleccionado é inválido!");
        }else{
            getTransactions(getType());
        }
    }

    Observable<DialogEvent> observeInitialDateCalendarDialog(){
        return openInitalDateCalendarObservable;
    }

    Observable<DialogEvent> observeFinalDateCalendarDialog(){
        return openFinalDateCalendarObservable;
    }

    public void getTransactions(String type) {
        Disposable disposable;
        if (type.equals(getResourceProvider().getString(R.string.transactions_menu_buy))) {
            disposable = transactionsManager.getBuyTransactionsFiltered(0, 20, fromDate, toDate)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else if (type.equals(getResourceProvider().getString(R.string.transactions_menu_sell))){
            disposable = transactionsManager.getSellTransactionsFiltered(0, 20, fromDate, toDate)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else{
            disposable = transactionsManager.getAllTransactionsFiltered(0,20, fromDate, toDate)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        }

        addDisposable(disposable);



    }

    private void onTransactionReceived(List<Transaction> transactions) {
        this.transactions.clear();
        this.transactions.addAll(transactions);

    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        getTransactionsFiltered();
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
        getTransactionsFiltered();

    }
}
