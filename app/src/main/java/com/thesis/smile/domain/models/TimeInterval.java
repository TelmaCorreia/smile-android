package com.thesis.smile.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TimeInterval implements Parcelable{

    private String id;
    private String from;
    private String to;
    private List<Integer> weekDays;
    private List<String> weekDaysString;
    private boolean activated;

    public TimeInterval(){ }

    public TimeInterval(String id, String from, String to, List<Integer> weekDays, List<String> weekDaysString, boolean activated) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.weekDays = weekDays;
        this.weekDaysString = weekDaysString;
        this.activated = activated;
    }

    protected TimeInterval(Parcel in) {
        id = in.readString();
        from = in.readString();
        to = in.readString();
        weekDaysString = in.createStringArrayList();
        activated = in.readByte() != 0;
        List<Integer> list = new ArrayList<>();
        in.readList(list, List.class.getClassLoader());
        weekDays = list;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeStringList(weekDaysString);
        parcel.writeByte((byte) (activated ? 1 : 0));
        parcel.writeList(weekDays);
    }
}
