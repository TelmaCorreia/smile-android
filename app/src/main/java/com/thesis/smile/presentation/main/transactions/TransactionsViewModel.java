package com.thesis.smile.presentation.main.transactions;

import com.thesis.smile.R;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class TransactionsViewModel extends BaseViewModel {

    private UserManager userManager;
    @Inject
    public TransactionsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.userManager = userManager;
    }

    public boolean isProsumer(){
        return userManager.getCurrentUser().getType().equals(getResourceProvider().getString(R.string.consumer))? false : true;
    }
}
