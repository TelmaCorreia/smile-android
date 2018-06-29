package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoricalDataRemote {

    @Expose
    private String timeDescription;
    @Expose
    @SerializedName("dataPoints")
    private List<HistoricalDataPointRemote> dataPoints;

    public HistoricalDataRemote(){}
    public HistoricalDataRemote(String timeDescription, List<HistoricalDataPointRemote> dataPoints) {
        this.timeDescription = timeDescription;
        this.dataPoints = dataPoints;
    }

    public String getTimeDescription() {
        return timeDescription;
    }

    public void setTimeDescription(String timeDescription) {
        this.timeDescription = timeDescription;
    }

    public List<HistoricalDataPointRemote> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<HistoricalDataPointRemote> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
