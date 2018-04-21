package com.thesis.smile.presentation.main.transactions.info_price;

import android.databinding.Bindable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class InfoPriceViewModel extends BaseViewModel {

    private List<Integer> selectedDays = new ArrayList<>();
    private PublishRelay<NavigationEvent> closeObservable = PublishRelay.create();

    @Inject
    public InfoPriceViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }

    @Bindable
    public String getPriceOne() {
        return "0.1894"; //fixme
    }


    @Bindable
    public String getPriceTwo() {
        return "0.0982"; //fixme
    }

    @Bindable
    public String getLink() {
        return "https://www.eem.pt/media/324637/monofolha_tarif_btn_ts_2018.pdf"; //fixme
    }

    public void onCloseClick(){
        closeObservable.accept(new NavigationEvent());
    }

    Observable<NavigationEvent> observeClose(){
        return closeObservable;
    }
}
