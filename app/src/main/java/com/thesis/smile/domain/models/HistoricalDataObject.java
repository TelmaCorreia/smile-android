package com.thesis.smile.domain.models;

import java.util.List;

public class HistoricalDataObject {

    private List<HistoricalData> data;

    public HistoricalDataObject(){}

    public HistoricalDataObject(List<HistoricalData> data) {
        this.data = data;
    }

    public List<HistoricalData> getData() {
        return data;
    }

    public void setData(List<HistoricalData> data) {
        this.data = data;
    }

}
