package com.thesis.smile.presentation.main.transactions.timers;

import android.databinding.Bindable;

import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TimersViewModel extends BaseToolbarViewModel {

    private String from = "";
    private String to = "";
    private List<Integer> selectedDays = new ArrayList<>();


    @Inject
    public TimersViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }

    @Bindable
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
        notifyPropertyChanged(BR.from);
        notifyPropertyChanged(BR.saveEnabled);

    }

    @Bindable
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
        notifyPropertyChanged(BR.to);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public List<Integer> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(List<Integer> selectedDays) {
        this.selectedDays = selectedDays;
        notifyPropertyChanged(BR.selectedDays);
        notifyPropertyChanged(BR.saveEnabled);
    }

    public void onTimerFromClick(){

    }
    public void onTimerToClick(){

    }

    public void onWeekDaysPickerClick(){

    }

    @Bindable
    public boolean isSaveEnabled() {
       return !(from.isEmpty() || to.isEmpty() || selectedDays.isEmpty());
    }

    public void onSaveClick(){

    }


}
