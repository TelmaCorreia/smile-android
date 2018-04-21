package com.thesis.smile.presentation.main.transactions.timers;

import android.databinding.Bindable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TimersViewModel extends BaseToolbarViewModel {

    private String from = "";
    private String to = "";
    private List<Integer> selectedDays = new ArrayList<>();
    private List<String> selectedDaysStrings = new ArrayList<>();


    TimeInterval timeInterval;
    private PublishRelay<DialogEvent> timerFromDialogObservable = PublishRelay.create();
    private PublishRelay<DialogEvent> timerToDialogObservable = PublishRelay.create();
    private PublishRelay<Event> saveObservable = PublishRelay.create();


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

    public void setSelectedDaysStrings(List<String> selectedDays) {
        this.selectedDaysStrings = selectedDays;
    }

    public void onTimerFromClick(){
        timerFromDialogObservable.accept(new DialogEvent());
    }
    public void onTimerToClick(){
        timerToDialogObservable.accept(new DialogEvent());
    }

    public void onWeekDaysPickerClick(){
    }

    @Bindable
    public boolean isSaveEnabled() {
       return !(from.isEmpty() || to.isEmpty() || selectedDays.isEmpty());
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void onSaveClick(){
        timeInterval = new TimeInterval(from, to, selectedDays, selectedDaysStrings );
        saveObservable.accept(new Event());
    }

    Observable<DialogEvent> observeTimerFromDialog(){
        return timerFromDialogObservable;
    }

    Observable<DialogEvent> observeTimerToDialog(){
        return timerToDialogObservable;
    }
    Observable<Event> observeSave(){
        return saveObservable;
    }


}
