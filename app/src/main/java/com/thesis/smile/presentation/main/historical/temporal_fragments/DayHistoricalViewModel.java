package com.thesis.smile.presentation.main.historical.temporal_fragments;

import android.databinding.Bindable;

import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class DayHistoricalViewModel extends BaseViewModel {

    @Inject
    public DayHistoricalViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                  UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);

    }

    @Bindable
    public String getTotalEnergyConsumption(){
        return "1.0";
    }

    @Bindable
    public String getTotalEnergyProduction(){
        return "1.0";
    }

    @Bindable
    public String getTotalEnergySurplus(){
        return "1.0";
    }

    @Bindable
    public String getEnergySurplusSoldNeighbors(){
        return "1.0";
    }

    @Bindable
    public String getEnergySurplusNotUsed(){
        return "1.0";
    }

    @Bindable
    public String getTotalAutoConsumption(){
        return "1.0";
    }

    @Bindable
    public String getAutoConsumptionBattery(){
        return "1.0";
    }

    @Bindable
    public String getAutoConsumptionPanels(){
        return "1.0";
    }

    @Bindable
    public String getTotalEnergyBought(){
        return "1.0";
    }

    @Bindable
    public String getEnergyBoughtNeighbors(){
        return "1.0";
    }

    @Bindable
    public String getEnergyBoughtEem(){
        return "1.0";
    }
}
