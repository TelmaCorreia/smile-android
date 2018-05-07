package com.thesis.smile.presentation.authentication.register.energy.info;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CycleInfoViewModel extends BaseViewModel {

    private PublishRelay<NavigationEvent> closeObservable = PublishRelay.create();
    private PublishRelay<Event> emailObservable = PublishRelay.create();
    private PublishRelay<Event> formObservable = PublishRelay.create();
    private PublishRelay<Event> telObservable = PublishRelay.create();

    @Inject
    public CycleInfoViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }


    public void onCloseClick(){
        closeObservable.accept(new NavigationEvent());
    }

    public void onEmailClick(){
        emailObservable.accept(new NavigationEvent());
    }

    public void onFormClick(){
        formObservable.accept(new NavigationEvent());
    }

    public void onTelClick(){
        telObservable.accept(new NavigationEvent());
    }

    Observable<NavigationEvent> observeClose(){
        return closeObservable;
    }
    Observable<Event> observeEmail(){
        return emailObservable;
    }

    Observable<Event> observeForm(){
        return formObservable;
    }
    Observable<Event> observeTel(){
        return telObservable;
    }



}
