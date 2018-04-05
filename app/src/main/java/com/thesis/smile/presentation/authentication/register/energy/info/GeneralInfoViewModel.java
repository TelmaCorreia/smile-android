package com.thesis.smile.presentation.authentication.register.energy.info;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GeneralInfoViewModel extends BaseViewModel {

    private PublishRelay<NavigationEvent> closeObservable = PublishRelay.create();

    @Inject
    public GeneralInfoViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }

    public void onCloseClick(){
        closeObservable.accept(new NavigationEvent());
    }

    Observable<NavigationEvent> observeClose(){
        return closeObservable;
    }

}
