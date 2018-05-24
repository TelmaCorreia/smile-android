package com.thesis.smile.presentation.main.transactions.historical_transactions;

import android.databinding.Bindable;
import android.databinding.ObservableList;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
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
    private UserManager userManager;
    private String type;
    private String timePeriod;
    private String initialDate;
    private String finalDate;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Totals totals;
    private int page=0;

    @Inject
    public HistoricalTransactionsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsManager transactionsManager, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.transactionsManager = transactionsManager;
        this.userManager = userManager;
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
    public int getPeriodVisible() {
        if (timePeriod != null && timePeriod.equals(getResourceProvider().getString(R.string.transactions_specific_period))){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    @Bindable
    public int getEmptyViewVisible() {
        if (transactions.size()==0 ){
            return View.VISIBLE;
        }
        return View.INVISIBLE;
    }

    @Bindable
    public int getUserTypeProsumer(){
        return userManager.getCurrentUser().getType().equals(getResourceProvider().getString(R.string.consumer))? View.GONE : View.VISIBLE;

    }

   /* @Bindable
    public float getPercentage(){
        return getUserTypeProsumer()==View.GONE?1.0f:0.5f;
    }
*/


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
        notifyPropertyChanged(BR.periodVisible);
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
        notifyPropertyChanged(BR.periodVisible);
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
            disposable = transactionsManager.getBuyTransactionsFiltered(page, 20, fromDate, toDate)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else if (type.equals(getResourceProvider().getString(R.string.transactions_menu_sell))){
            disposable = transactionsManager.getSellTransactionsFiltered(page, 20, fromDate, toDate)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        } else{
            disposable = transactionsManager.getAllTransactionsFiltered(page,20, fromDate, toDate)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTransactionReceived, this::onError);
        }

        addDisposable(disposable);

    }


    public Observable<List<Transaction>> getAllTransactions() {
        return getTransactionsObservable();
    }
    private Observable<List<Transaction>> getTransactionsObservable() {
        return transactionsManager.getAllTransactions(page, Constants.PAGE_SIZE, fromDate, toDate)
                .filter(userList -> !isLastPage(userList))
                .flatMap(this::getNextPageTransactionsObservable);
    }
    private Observable<List<Transaction>> getNextPageTransactionsObservable(final List<Transaction> transactions) {
        Observable<List<Transaction>> transactionsPageObservable = Observable.just(transactions);
        this.page+=page+Constants.PAGE_SIZE;
        Observable<List<Transaction>> nextTransactionsPageObservable = getTransactionsObservable();
        return Observable.merge(nextTransactionsPageObservable, transactionsPageObservable);
    }

    private boolean isLastPage(List<Transaction> transactions1) {
        return transactions1.isEmpty();
    }

    private void onTransactionReceived(List<Transaction> transactions) {
        this.transactions.clear();
        this.transactions.addAll(transactions);
        notifyPropertyChanged(BR.emptyViewVisible);

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
