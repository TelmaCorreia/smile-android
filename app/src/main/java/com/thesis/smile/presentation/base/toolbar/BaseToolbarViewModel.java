package com.thesis.smile.presentation.base.toolbar;

import android.databinding.Bindable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import io.reactivex.Observable;

public class BaseToolbarViewModel extends BaseViewModel {


    private String toolbarTitle = "";

    private PublishRelay<NavigationEvent> clickActionObservable = PublishRelay.create();

    public BaseToolbarViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }

    @Bindable
    public String getToolbarTitle() {
        return this.toolbarTitle;
    }

    public void setToolbarTitle(String toolbarTitle){
        this.toolbarTitle = toolbarTitle;
    }

    public void onActionClick(){
        clickActionObservable.accept(new NavigationEvent());
    }

    public Observable<NavigationEvent> observeAction(){
        return clickActionObservable;
    }


}

