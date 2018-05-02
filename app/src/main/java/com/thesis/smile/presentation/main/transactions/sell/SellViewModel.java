package com.thesis.smile.presentation.main.transactions.sell;

import android.databinding.Bindable;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableList;
import android.widget.RadioGroup;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsSettingsManager;
import com.thesis.smile.domain.models.BuySettings;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.SellSettings;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;
import com.thesis.smile.BR;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SellViewModel extends BaseViewModel {

    private String batteryLevel;
    private String etBatteryLevel;
    private boolean option1 = false;
    private boolean option2 = false;
    private final ExclusiveObservableList<TimeInterval> timeIntervals;
    private final List<Neighbour> neighbours;

    private SellSettings sellSettings;
    private SellSettings previousSettings;

    private TransactionsSettingsManager sellSettingsManager;

    private PublishRelay<NavigationEvent> openPriceInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openTimerObservable = PublishRelay.create();
    private PublishRelay<Event> neighboursChanged = PublishRelay.create();

    @Inject
    public SellViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents,  TransactionsSettingsManager sellSettingsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.sellSettingsManager = sellSettingsManager;
        timeIntervals = new ExclusiveObservableList<>();
        neighbours = new ArrayList<>();
        getTimeIntervalsFromServer();
        getNeighboursFromServer();
        getSellSettingsFromServer();
    }
    @Bindable
    public boolean isSell() {
        if (sellSettings!=null){
            return sellSettings.isOn();
        }
        return false;
    }

    public void setSell(boolean sell) {
        if(sellSettings!=null) {
            this.sellSettings.setOn(sell);
            notifyPropertyChanged(BR.sell);
            notifyPropertyChanged(BR.saveVisible);
        }
    }

    @Bindable
    public boolean isConcretePriceEditable() {
        return option1;
    }


    @Bindable
    public boolean isPlusPriceEditable() {
        return option2;
    }

    public String getBatteryLevel() {
        if(sellSettings!=null){
            return String.valueOf(sellSettings.getBatteryLevel());
        }
        return null;
    }

    public void setBatteryLevel(String batteryLevel) {
        if(sellSettings!=null){
            sellSettings.setBatteryLevel(Double.parseDouble(batteryLevel.replace(',', '.')));
            notifyPropertyChanged(BR.saveVisible);
            notifyPropertyChanged(BR.saveVisible);
        }
    }

    public void onPriceInfoClick(){
        openPriceInfoObservable.accept(new NavigationEvent());
    }

    public void onPriceChanged(RadioGroup radioGroup, int id){
        option1 = !option1;
        option2 = !option2;
        if(sellSettings!=null){
            sellSettings.setSpecificPrice(option1);
            sellSettings.setPlusPrice(option2);
        }
        notifyPropertyChanged(BR.plusPriceEditable);
        notifyPropertyChanged(BR.concretePriceEditable);
        notifyPropertyChanged(BR.saveVisible);

    }

    public void onAddTimerClick(){
        openTimerObservable.accept(new NavigationEvent());
    }

    public void addTimeInterval(TimeInterval timeInterval, boolean update){
        Disposable disposable;
        if (!update){
            disposable = sellSettingsManager.postTimeIntervalSell(timeInterval)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTimeIntervalReceived, this::onError);
        }else{
            disposable = sellSettingsManager.updateTimeInterval(timeInterval)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTimeIntervalUpdatedReceived, this::onError);
        }

        addDisposable(disposable);

    }

    private void onTimeIntervalUpdatedReceived(TimeInterval timeInterval) {
        getTimeIntervalsFromServer();
    }

    private void onTimeIntervalReceived(TimeInterval timeInterval) {
        if (timeIntervals!=null){
            timeIntervals.add(timeInterval);
        }
    }

    private void onNeighboursReceived(List<Neighbour> neighbours) {
        this.neighbours.addAll(neighbours);
        neighboursChanged.accept(new Event());

    }

    private void onTimeIntervalsReceived(List<TimeInterval> timeIntervals) {
        this.timeIntervals.clear();
        this.timeIntervals.addAll(timeIntervals);
    }

    public void removeTimerInterval(TimeInterval timeInterval){

        Disposable disposable = sellSettingsManager.deleteTimeInterval(timeInterval)
                .compose(loadingTransformCompletable())
                .compose(schedulersTransformCompletableIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onTimeIntervalRemoved, this::onError);
        addDisposable(disposable);

    }

    private void onTimeIntervalRemoved() {
        getTimeIntervalsFromServer();
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

    public ObservableList<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }


    public void getTimeIntervalsFromServer(){
        Disposable disposableTimeIntervals = sellSettingsManager.getTimeIntervalsSell()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onTimeIntervalsReceived, this::onError);

        addDisposable(disposableTimeIntervals);
    }

    public void getNeighboursFromServer(){
        Disposable disposableNeighbours = sellSettingsManager.getNeighboursSell(0, 20)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onNeighboursReceived, this::onError);
        addDisposable(disposableNeighbours);
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }


    public void onSaveClick() {
        //TODO: price verifications
        sellSettingsManager.updateSellSettings(sellSettings)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onUpdateComplete, this::onError);

    }

    private void onUpdateComplete(SellSettings sellSettings) {

        getUiEvents().showToast(getResourceProvider().getString(R.string.msg_update_sucess));
        this.previousSettings = new SellSettings(sellSettings.getId(), sellSettings.isOn(), sellSettings.isSpecificPrice(), sellSettings.isPlusPrice(), sellSettings.getSpecificPriceValue(), sellSettings.getPlusPriceValue(), sellSettings.getBatteryLevel(), sellSettings.isAllNeighboursSelected());
        this.sellSettings = sellSettings;
        notifyPropertyChanged(BR.saveVisible);
    }

    public void getSellSettingsFromServer() {
        sellSettingsManager.getSellSettings()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onSellSettingsReceived, this::onError);
    }

    private void onSellSettingsReceived(SellSettings sellSettings) {
        this.sellSettings = sellSettings;
        this.option1= sellSettings.isSpecificPrice();
        this.option2=sellSettings.isPlusPrice();
        this.previousSettings = new SellSettings(sellSettings.getId(), sellSettings.isOn(), sellSettings.isSpecificPrice(), sellSettings.isPlusPrice(), sellSettings.getSpecificPriceValue(), sellSettings.getPlusPriceValue(), sellSettings.getBatteryLevel(), sellSettings.isAllNeighboursSelected());
        notifyChange();
    }

    @Bindable
    public boolean isSaveVisible() {
        if(sellSettings!=null) {
            return !(sellSettings.isOn()== previousSettings.isOn()
                    && sellSettings.isAllNeighboursSelected()==sellSettings.isAllNeighboursSelected()
                    && sellSettings.isSpecificPrice()==previousSettings.isSpecificPrice()
                    && sellSettings.isPlusPrice()==previousSettings.isPlusPrice()
                    && sellSettings.getSpecificPriceValue()==previousSettings.getSpecificPriceValue()
                    && sellSettings.getPlusPriceValue()==previousSettings.getPlusPriceValue()
                    && sellSettings.getBatteryLevel()==previousSettings.getBatteryLevel());
        }
        return false;

    }
}
