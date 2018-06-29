package com.thesis.smile.data.remote.models;


import com.google.gson.annotations.Expose;

import java.util.List;

public class HistoricalDataObjectRemote {

    @Expose
    private List<HistoricalDataRemote> data;

    public HistoricalDataObjectRemote(){}

    public HistoricalDataObjectRemote(List<HistoricalDataRemote> data) {
        this.data = data;
    }

    public List<HistoricalDataRemote> getData() {
        return data;
    }

    public void setData(List<HistoricalDataRemote> data) {
        this.data = data;
    }

}
