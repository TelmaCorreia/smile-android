package com.thesis.smile.presentation.main.historical.temporal_fragments;

import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;


public class WeekHistoricalViewModel extends BaseViewModel {

    @Inject
    public WeekHistoricalViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                   UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }




}
