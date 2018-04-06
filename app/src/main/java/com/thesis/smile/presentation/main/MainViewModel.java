package com.thesis.smile.presentation.main;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.main.menu_events.OpenHistoricalEvent;
import com.thesis.smile.presentation.main.menu_events.OpenHomeEvent;
import com.thesis.smile.presentation.main.menu_events.OpenMenuEvent;
import com.thesis.smile.presentation.main.menu_events.OpenRankingEvent;
import com.thesis.smile.presentation.main.menu_events.OpenTransactionsEvent;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MainViewModel extends BaseViewModel {

    private BehaviorRelay<OpenMenuEvent> openMenuObservable = BehaviorRelay.create();

    @Inject
    public MainViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);

        openMenuObservable.accept(new OpenHomeEvent(true));
    }

    public void onHomeClick(){
        openMenu(new OpenHomeEvent(true));
    }

    public void onHistoricalClick(){
        openMenu(new OpenHistoricalEvent(true));
    }

    public void onRankingClick(){
        openMenu(new OpenRankingEvent(true));
    }

    public void onTransactionsClick(){
        openMenu(new OpenTransactionsEvent(true));
    }

    Observable<OpenMenuEvent> observeOpenMenu() {
        return openMenuObservable;
    }

    private void openMenu(OpenMenuEvent event) {
        if (openMenuObservable.getValue().getMenuType() != event.getMenuType() || event.mustClearStack()) {
            openMenuObservable.accept(event);
        }
    }
}
