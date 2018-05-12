package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuySettingsRemote {

    @Expose
    private String id;

    @Expose
    private boolean on;

    @Expose
    @SerializedName("eem_price")
    private boolean eemPrice;

    @Expose
    @SerializedName("eem_plus_price")
    private boolean eemPlusPrice;

    @Expose
    @SerializedName("eem_price_value")
    private double eemPriceValue;

    @Expose
    @SerializedName("emm_plus_price_value")
    private double eemPlusPriceValue;

    @Expose
    @SerializedName("all_neighbours_selected")
    private boolean allNeighboursSelected;

    public BuySettingsRemote() {
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


