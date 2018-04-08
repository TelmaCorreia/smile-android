package com.thesis.smile.presentation.main.home;

import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;

import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class HomeDetailsViewModel extends BaseToolbarViewModel {

    @Inject
    public HomeDetailsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);

    }

}
