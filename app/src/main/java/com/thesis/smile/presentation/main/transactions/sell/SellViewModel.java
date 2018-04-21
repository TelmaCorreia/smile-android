package com.thesis.smile.presentation.main.transactions.sell;

import android.databinding.Bindable;
import android.databinding.ObservableList;
import android.widget.RadioGroup;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;
import com.thesis.smile.BR;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SellViewModel extends BaseViewModel {

    private String batteryLevel;
    private String etBatteryLevel;
    private boolean sell = true;
    private boolean option1 = true;
    private boolean option2 = false;
    private final ExclusiveObservableList<TimeInterval> timeIntervals;



    private PublishRelay<NavigationEvent> openPriceInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openTimerObservable = PublishRelay.create();


    @Inject
    public SellViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
        timeIntervals = new ExclusiveObservableList<>();
    }
    @Bindable
    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
        notifyPropertyChanged(BR.sell);
    }

    @Bindable
    public boolean isConcretePriceEditable() {
        return option1;
    }


    @Bindable
    public boolean isPlusPriceEditable() {
        return option2;
    }

    @Bindable
    public String getBatteryLevel() {
        return String.valueOf(batteryLevel);
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

   /* @Bindable
    public String getEtBatteryLevel() {
        return etBatteryLevel;
    }

    public void setEtBatteryLevel(String etBatteryLevel) {
        this.etBatteryLevel = etBatteryLevel;
    }*/

    public void onCheckedChanged(boolean checked) {
        this.sell=checked;
    }

    public void onPriceInfoClick(){
        openPriceInfoObservable.accept(new NavigationEvent());
    }

    public void onPriceChanged(RadioGroup radioGroup, int id){
        option1 = !option1;
        option2 = !option2;
        notifyPropertyChanged(BR.plusPriceEditable);
        notifyPropertyChanged(BR.concretePriceEditable);

    }

    public void onAddTimerClick(){
        openTimerObservable.accept(new NavigationEvent());
    }

    public void addTimeInterval(TimeInterval timeInterval){
        if (timeIntervals!=null){
            timeIntervals.add(timeInterval);
        }
    }

    public void removeTimerInterval(TimeInterval timeInterval){
        if (timeIntervals!=null){
            timeIntervals.remove(timeInterval);
        }
    }

    Observable<NavigationEvent> observeOpenPriceInfo(){
        return openPriceInfoObservable;
    }

    Observable<NavigationEvent> observeOpenTimer(){
        return openTimerObservable;
    }

    public ObservableList<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }


}
