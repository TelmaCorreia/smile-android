package com.thesis.smile.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TimeInterval implements Parcelable{

    private String from;
    private String to;
    private List<Integer> weekDays;
    private List<String> weekDaysString;
    private boolean activated;

    public TimeInterval(){ }

    public TimeInterval(String from, String to, List<Integer> weekdays, List<String> weekDaysString) {
        this.from = from;
        this.to = to;
        this.weekDays = weekdays;
        this.weekDaysString = weekDaysString;
        this.activated = true;
    }

    protected TimeInterval(Parcel in) {
        from = in.readString();
        to = in.readString();
        weekDaysString = in.createStringArrayList();
        activated = in.readByte() != 0;
    }

    public static final Creator<TimeInterval> CREATOR = new Creator<TimeInterval>() {
        @Override
        public TimeInterval createFromParcel(Parcel in) {
            return new TimeInterval(in);
        }

        @Override
        public TimeInterval[] newArray(int size) {
            return new TimeInterval[size];
        }
    };

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

    public List<Integer> getWeekdays() {
        return weekDays;
    }

    public void setWeekdays(List<Integer> weekdays) {
        this.weekDays = weekdays;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeStringList(weekDaysString);
        parcel.writeByte((byte) (activated ? 1 : 0));
    }
}
