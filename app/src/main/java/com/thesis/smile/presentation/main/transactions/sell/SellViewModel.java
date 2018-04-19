package com.thesis.smile.presentation.main.transactions.sell;

import android.databinding.Bindable;
import android.widget.RadioGroup;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;
import com.thesis.smile.BR;


import javax.inject.Inject;

import io.reactivex.Observable;

public class SellViewModel extends BaseViewModel {

    private String batteryLevel;
    private String etBatteryLevel;
    private boolean sell = true;
    private boolean option1 = true;
    private boolean option2 = false;

    private PublishRelay<NavigationEvent> openPriceInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openTimerObservable = PublishRelay.create();


    @Inject
    public SellViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
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

    Observable<NavigationEvent> observeOpenPriceInfo(){
        return openPriceInfoObservable;
    }

    Observable<NavigationEvent> observeOpenTimer(){
        return openTimerObservable;
    }


}
