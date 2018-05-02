package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalsRemote {

    @Expose
    @SerializedName("total_sold")
    private double totalSold;

    @Expose
    @SerializedName("total_bought")
    private double totalBought;

    public TotalsRemote(){}

    public double getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(double totalSold) {
        this.totalSold = totalSold;
    }

    public double getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(double totalBought) {
        this.totalBought = totalBought;
    }
}
