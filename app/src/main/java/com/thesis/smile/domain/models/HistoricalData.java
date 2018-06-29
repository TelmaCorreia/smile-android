package com.thesis.smile.domain.models;

import java.util.List;

public class HistoricalData {

    private String timeDescription;
    private List<HistoricalDataPoint> dataPoints;

    public HistoricalData(){}

    public HistoricalData(String timeDescription, List<HistoricalDataPoint> dataPoints) {
        this.timeDescription = timeDescription;
        this.dataPoints = dataPoints;
    }

    public String getTimeDescription() {
        return timeDescription;
    }

    public void setTimeDescription(String timeDescription) {
        this.timeDescription = timeDescription;
    }

    public List<HistoricalDataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<HistoricalDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

}
