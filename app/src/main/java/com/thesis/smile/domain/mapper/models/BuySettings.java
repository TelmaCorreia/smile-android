package com.thesis.smile.domain.mapper.models;

public class BuySettings {

    private String id;

    private boolean on;

    private boolean eemPrice;

    private boolean eemPlusPrice;

    private double eemPriceValue;

    private double eemPlusPriceValue;

    private boolean allNeighboursSelected;

    public BuySettings() {
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

    public boolean isEemPrice() {
        return eemPrice;
    }

    public void setEemPrice(boolean eemPrice) {
        this.eemPrice = eemPrice;
    }

    public boolean isEemPlusPrice() {
        return eemPlusPrice;
    }

    public void setEemPlusPrice(boolean eemPlusPrice) {
        this.eemPlusPrice = eemPlusPrice;
    }

    public double getEemPriceValue() {
        return eemPriceValue;
    }

    public void setEemPriceValue(double eemPriceValue) {
        this.eemPriceValue = eemPriceValue;
    }

    public double getEemPlusPriceValue() {
        return eemPlusPriceValue;
    }

    public void setEemPlusPriceValue(double eemPlusPriceValue) {
        this.eemPlusPriceValue = eemPlusPriceValue;
    }

    public boolean isAllNeighboursSelected() {
        return allNeighboursSelected;
    }

    public void setAllNeighboursSelected(boolean allNeighboursSelected) {
        this.allNeighboursSelected = allNeighboursSelected;
    }
}
