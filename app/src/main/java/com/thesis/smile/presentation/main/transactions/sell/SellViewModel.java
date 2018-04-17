package com.thesis.smile.presentation.main.transactions.sell;

import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class SellViewModel extends BaseViewModel {

    @Inject
    public SellViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }


}
