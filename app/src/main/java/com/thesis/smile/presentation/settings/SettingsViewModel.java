package com.thesis.smile.presentation.settings;

import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class SettingsViewModel extends BaseToolbarViewModel {

    @Inject
    public SettingsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }
}
