package com.thesis.smile.presentation.timers.timer_list;

import android.databinding.Bindable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.BaseViewModelInstance;
import com.thesis.smile.presentation.utils.actions.events.Event;

import java.util.List;

public class TimeIntervalItemViewModel extends BaseViewModelInstance{

    private TimeInterval timeInterval;
    private PublishRelay<Event> removeObservavle = PublishRelay.create();
    private TimeIntervalAdapter.OnItemClickListener onItemClickListener;
    private TimeIntervalAdapter.OnRemoveClickListener onRemoveClickListener;
    private TimeIntervalAdapter.OnStateChangeClickListener onStateChangeClickListener;

    public TimeIntervalItemViewModel(TimeInterval timeInterval, TimeIntervalAdapter.OnItemClickListener onItemClickListener, TimeIntervalAdapter.OnRemoveClickListener onRemoveClickListener, TimeIntervalAdapter.OnStateChangeClickListener onStateChangeClickListener) {
        this.timeInterval = timeInterval;
        this.onItemClickListener = onItemClickListener;
        this.onRemoveClickListener = onRemoveClickListener;
        this.onStateChangeClickListener = onStateChangeClickListener;
    }

    @Bindable
    public String getFrom() {
        return timeInterval.getFrom();
    }
    @Bindable
    public String getTo() {
        return timeInterval.getTo();
    }
    @Bindable
    public boolean isActivated(){
        return timeInterval.isActivated();
    }

    public void setActivated(boolean activated){
        timeInterval.setActivated(activated);
    }
    @Bindable
    public  String getWeekDays(){
        List<String> days = timeInterval.getWeekDaysString();
        String result = "";

        for(int i=0; i< days.size(); i++){
            if (i==days.size()-2){
                result += days.get(i) + " e ";
            }else if (i < days.size()-1){
                result += days.get(i)+", ";
            }else {
                result += days.get(i);
            }
        }

        return result;

    }

    public void onDeleteTimerClick(){
        if (onRemoveClickListener != null) {
            onRemoveClickListener.onRemoveClick(timeInterval);
        }
    }


    public void onTimerClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(timeInterval);
        }
    }

    public void onStateChangeClick() {
        if (onStateChangeClickListener != null) {
            onStateChangeClickListener.onStateClick(timeInterval);
        }
    }



}
