package com.thesis.smile.presentation.iota_settings.pager_fragments;

import android.databinding.Bindable;
import android.view.View;

import com.thesis.smile.R;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class MonthIotaViewModel extends BaseViewModel {

    private UserManager userManager;

    @Inject
    public MonthIotaViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                              UiEvents uiEvents, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.userManager = userManager;
    }

    @Bindable
    public int getUserTypeProsumer(){
        return userManager.getCurrentUser().getType().equals(getResourceProvider().getString(R.string.consumer))? View.GONE : View.VISIBLE;

    }

    @Bindable
    public String getIncome() {
       /* if (totals!=null){
            return String.format("%.2f", totals.getTotalSold()) + getResourceProvider().getString(R.string.coin);
        }*/
        return null;
    }

    @Bindable
    public String getOutcome() {
        return null;
    }


}
