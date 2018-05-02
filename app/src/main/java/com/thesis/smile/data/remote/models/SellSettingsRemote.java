package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellSettingsRemote {

    @Expose
    private String id;

    @Expose
    private boolean on;

    @Expose
    @SerializedName("specific_price")
    private boolean specificPrice;

    @Expose
    @SerializedName("plus_price")
    private boolean plusPrice;

    @Expose
    @SerializedName("specific_price_value")
    private double specificPriceValue;

    @Expose
    @SerializedName("plus_price_value")
    private double plusPriceValue;

    @Expose
    @SerializedName("batteryLevel")
    private double batteryLevel;

    @Expose
    @SerializedName("all_neighbours_selected")
    private boolean allNeighboursSelected;

    public SellSettingsRemote() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean isSpecificPrice() {
        return specificPrice;
    }

    public void setSpecificPrice(boolean specificPrice) {
        this.specificPrice = specificPrice;
    }

    public boolean isPlusPrice() {
        return plusPrice;
    }

    public void setPlusPrice(boolean plusPrice) {
        this.plusPrice = plusPrice;
    }

    public double getSpecificPriceValue() {
        return specificPriceValue;
    }

    public void setSpecificPriceValue(double specificPriceValue) {
        this.specificPriceValue = specificPriceValue;
    }

    public double getPlusPriceValue() {
        return plusPriceValue;
    }

    public void setPlusPriceValue(double plusPriceValue) {
        this.plusPriceValue = plusPriceValue;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public boolean isAllNeighboursSelected() {
        return allNeighboursSelected;
    }

    public void setAllNeighboursSelected(boolean allNeighboursSelected) {
        this.allNeighboursSelected = allNeighboursSelected;
    }


}


