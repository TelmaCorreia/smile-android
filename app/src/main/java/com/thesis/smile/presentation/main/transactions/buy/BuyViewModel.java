package com.thesis.smile.presentation.main.transactions.buy;

import android.databinding.Bindable;
import android.widget.RadioGroup;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class BuyViewModel extends BaseViewModel {

    private boolean buy = true;
    private boolean option1 = true;
    private boolean option2 = false;

    private PublishRelay<NavigationEvent> openPriceInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openTimerObservable = PublishRelay.create();

    @Inject
    public BuyViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }

    @Bindable
    public boolean isPlusPriceEditable() {
        return option2;
    }

    public void onCheckedChanged(boolean checked) {
        this.buy=checked;
    }

    public void onPriceChanged(RadioGroup radioGroup, int id){
        option1 = !option1;
        option2 = !option2;
        notifyPropertyChanged(BR.plusPriceEditable);

    }

    public void onPriceInfoClick(){

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
