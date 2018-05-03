package com.thesis.smile.presentation.main.transactions.buy;

import android.databinding.Bindable;
import android.databinding.ObservableList;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsSettingsManager;
import com.thesis.smile.domain.models.BuySettings;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class BuyViewModel extends BaseViewModel {

    private final ExclusiveObservableList<TimeInterval> timeIntervals;
    private final List<Neighbour> neighbours;
    private BuySettings buySettings;
    private BuySettings previousSettings;

    private TransactionsSettingsManager buySettingsManager;

    private PublishRelay<NavigationEvent> openPriceInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openTimerObservable = PublishRelay.create();
    private PublishRelay<Event> neighboursChanged = PublishRelay.create();
    private PublishRelay<Event> settingsChanged = PublishRelay.create();


    @Inject
    public BuyViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsSettingsManager buySettingsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.buySettingsManager = buySettingsManager;
        timeIntervals = new ExclusiveObservableList<>();
        neighbours = new ArrayList<>();
        getTimeIntervalsFromServer();
        getNeighboursFromServer();
        getBuySettingsFromServer();
    }

    @Bindable
    public boolean isBuy() {
        if(buySettings!=null){
            return buySettings.isOn();
        }
        return false;
    }

    public void setBuy(boolean buy) {
        if(buySettings!=null) {
            this.buySettings.setOn(buy);
            notifyPropertyChanged(BR.buy);
            notifyPropertyChanged(BR.saveVisible);
        }
    }

    @Bindable
    public boolean isPlusPriceEditable() {
        if(buySettings!=null){
            return buySettings.isEemPlusPrice();
        }
        return false;
    }

    @Bindable
    public String getPlusPriceValue() {
        if(buySettings!=null){
            return String.format("%.2f", buySettings.getEemPlusPriceValue());
        }
        return null;
    }

    public void setPlusPriceValue(String value) {
        if(buySettings!=null) {
            // this.buySettings.setEemPlusPriceValue(Double.parseDouble(value.replace(',', '.')));
            //  notifyPropertyChanged(BR.plusPriceValue);
        }
        notifyPropertyChanged(BR.saveVisible);
    }

    public boolean isAllNeighboursSelected() {
        if(buySettings!=null){
            return buySettings.isAllNeighboursSelected();
        }
        return false;
    }

    public void setAllNeighboursSelected(boolean value) {
        if(buySettings!=null){
            buySettings.setAllNeighboursSelected(value);
            notifyPropertyChanged(BR.saveVisible);
        }
    }

    @Bindable
    public boolean isSaveVisible() {
        if(buySettings!=null) {
            return !(buySettings.isOn()== previousSettings.isOn()
                    && buySettings.isAllNeighboursSelected()==buySettings.isAllNeighboursSelected()
                    && buySettings.isEemPrice()==previousSettings.isEemPrice()
                    && buySettings.isEemPlusPrice()==previousSettings.isEemPlusPrice()
                    && buySettings.getEemPlusPriceValue()==previousSettings.getEemPlusPriceValue()
                    && buySettings.getEemPriceValue()==previousSettings.getEemPriceValue());
        }
        return false;
    }

    public void onEemPriceClick(){
        if(buySettings!=null){
            buySettings.setEemPrice(!buySettings.isEemPrice());
            buySettings.setEemPlusPrice(!buySettings.isEemPlusPrice());
            settingsChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
            notifyPropertyChanged(BR.plusPriceEditable);
        }
    }

    public void onEemPlusPriceClick(){
        if(buySettings!=null){
            buySettings.setEemPrice(!buySettings.isEemPrice());
            buySettings.setEemPlusPrice(!buySettings.isEemPlusPrice());
            settingsChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
            notifyPropertyChanged(BR.plusPriceEditable);
        }
    }

    public void onPriceInfoClick(){
        openPriceInfoObservable.accept(new NavigationEvent());
    }

    public void onAddTimerClick(){
        openTimerObservable.accept(new NavigationEvent());
    }

    public ObservableList<TimeInterval> getTimeIntervals() {
        return this.timeIntervals;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public BuySettings getBuySettings() {
        return buySettings;
    }

    /**
     * TIME INTERVALS
     * */

    public void getTimeIntervalsFromServer(){
        Disposable disposableTimeIntervals = buySettingsManager.getTimeIntervalsBuy()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onTimeIntervalsReceived, this::onError);

        addDisposable(disposableTimeIntervals);
    }

    private void onTimeIntervalsReceived(List<TimeInterval> timeIntervals) {
        this.timeIntervals.clear();
        this.timeIntervals.addAll(timeIntervals);
    }

    public void addTimeInterval(TimeInterval timeInterval, boolean update){
        Disposable disposable;
        if (!update){
            disposable = buySettingsManager.postTimeIntervalBuy(timeInterval)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTimeIntervalReceived, this::onError);
        }else{
            disposable = buySettingsManager.updateTimeInterval(timeInterval)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTimeIntervalUpdatedReceived, this::onError);
        }

        addDisposable(disposable);
    }

    private void onTimeIntervalReceived(TimeInterval timeInterval) {
        if (timeIntervals!=null){
            timeIntervals.add(timeInterval);
        }
    }

    private void onTimeIntervalUpdatedReceived(TimeInterval timeInterval) {
        getTimeIntervalsFromServer();

    }

    public void removeTimerInterval(TimeInterval timeInterval) {
        Disposable disposable = buySettingsManager.deleteTimeInterval(timeInterval)
                .compose(loadingTransformCompletable())
                .compose(schedulersTransformCompletableIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onTimeIntervalRemoved, this::onError);
        addDisposable(disposable);
    }

    private void onTimeIntervalRemoved() {
        getTimeIntervalsFromServer();
    }

    /**
     * NEIGHBOURS
     * **/

    public void getNeighboursFromServer(){
        Disposable disposableNeighbours = buySettingsManager.getNeighboursBuy(0, 20)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onNeighboursReceived, this::onError);

        addDisposable(disposableNeighbours);
    }

    private void onNeighboursReceived(List<Neighbour> neighbours) {
        this.neighbours.addAll(neighbours);
        neighboursChanged.accept(new Event());
    }


    /**
     * SETTINGS
     * **/

    public void getBuySettingsFromServer() {
        buySettingsManager.getBuySettings()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onBuySettingsReceived, this::onError);
        }

    private void onBuySettingsReceived(BuySettings buySettings) {
        this.buySettings = buySettings;
        this.previousSettings = new BuySettings(buySettings.getId(), buySettings.isOn(), buySettings.isEemPrice(), buySettings.isEemPlusPrice(), buySettings.getEemPriceValue(), buySettings.getEemPlusPriceValue(), buySettings.isAllNeighboursSelected());
        settingsChanged.accept(new Event());
        notifyChange();
    }

    /**
     * SAVE
     * **/

    public void onSaveClick() {
        //TODO: price verifications
        buySettingsManager.updateBuySettings(buySettings)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onUpdateComplete, this::onError);

    }

    private void onUpdateComplete(BuySettings buySettings) {

        getUiEvents().showToast(getResourceProvider().getString(R.string.msg_update_sucess));
        this.previousSettings = new BuySettings(buySettings.getId(), buySettings.isOn(), buySettings.isEemPrice(), buySettings.isEemPlusPrice(), buySettings.getEemPriceValue(), buySettings.getEemPlusPriceValue(), buySettings.isAllNeighboursSelected());
        this.buySettings = buySettings;
        notifyPropertyChanged(BR.saveVisible);
    }

    Observable<NavigationEvent> observeOpenPriceInfo(){
        return openPriceInfoObservable;
    }

    Observable<NavigationEvent> observeOpenTimer(){
        return openTimerObservable;
    }

    Observable<Event> observeNeighbours(){
        return neighboursChanged;
    }

    Observable<Event> observeBuySettings(){
        return settingsChanged;
    }
}
