package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeIntervalsRemote {

    @Expose
    @SerializedName("timeIntervals")
    private List<TimeIntervalRemote> timeIntervals;

    public TimeIntervalsRemote(){}

    public List<TimeIntervalRemote> getTimeIntervals() {
        return timeIntervals;
    }

    public void setTimeIntervals(List<TimeIntervalRemote> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }
}
