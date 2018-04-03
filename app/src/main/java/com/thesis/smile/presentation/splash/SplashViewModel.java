package com.thesis.smile.presentation.splash;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SplashViewModel extends BaseViewModel{

    private BehaviorRelay<NavigationEvent> openLoginObservable = BehaviorRelay.create();
    private BehaviorRelay<NavigationEvent> openMainObservable = BehaviorRelay.create();

    @Inject
    SplashViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);

        openLoginObservable.accept(new NavigationEvent());
    }

    Observable<NavigationEvent> observeOpenLogin() {
        return openLoginObservable;
    }

    Observable<NavigationEvent> observeOpenMain() {
        return openMainObservable;
    }
}
