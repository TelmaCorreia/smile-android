package com.thesis.smile.presentation.main.historical;

import com.thesis.smile.domain.managers.IotaManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class HistoricalViewModel extends BaseViewModel {

    @Inject
    public HistoricalViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager, IotaManager iotaManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

    }

}
