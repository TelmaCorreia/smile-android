package com.thesis.smile.presentation.main.home;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomeViewModel extends BaseToolbarViewModel {

    public PublishRelay<NavigationEvent> openHomeBoughtDetails = PublishRelay.create();
    public PublishRelay<NavigationEvent> openHomeSoldDetails = PublishRelay.create();


    @Inject
    public HomeViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }

    Observable<NavigationEvent> observeOpenHomeBoughtDetails(){
        return openHomeBoughtDetails;
    }

    Observable<NavigationEvent> observeOpenHomeSoldDetails(){
        return openHomeSoldDetails;
    }


}
