package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class TimeIntervalRemote {

    @Expose
    private String from;

    @Expose
    private String to;

    @Expose
    private List<Integer> weekDays;

    @Expose
    private List<String> weekDaysString;

    @Expose
    private boolean activated;

    public TimeIntervalRemote(){}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Integer> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<Integer> weekDays) {
        this.weekDays = weekDays;
    }

    public List<String> getWeekDaysString() {
        return weekDaysString;
    }

    public void setWeekDaysString(List<String> weekDaysString) {
        this.weekDaysString = weekDaysString;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
