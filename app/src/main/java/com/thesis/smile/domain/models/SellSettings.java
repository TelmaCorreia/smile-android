package com.thesis.smile.domain.models;

public class SellSettings {

    private String id;

    private boolean on;

    private boolean specificPrice;

    private boolean plusPrice;

    private double specificPriceValue;

    private double plusPriceValue;

    private double batteryLevel;

    private boolean allNeighboursSelected;

    public SellSettings() {
    }

    public SellSettings(String id, boolean on, boolean specificPrice, boolean plusPrice, double specificPriceValue, double plusPriceValue, double batteryLevel, boolean allNeighboursSelected) {
        this.id = id;
        this.on = on;
        this.specificPrice = specificPrice;
        this.plusPrice = plusPrice;
        this.specificPriceValue = specificPriceValue;
        this.plusPriceValue = plusPriceValue;
        this.batteryLevel = batteryLevel;
        this.allNeighboursSelected = allNeighboursSelected;
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
