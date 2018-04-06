package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

public class EnergyParamsRemote {
    @Expose
    private String category;
    @Expose
    private String power;
    @Expose
    private String tariff;
    @Expose
    private String cycle;

    public EnergyParamsRemote(String category, String power, String tariff, String cycle) {
        this.category = category;
        this.power = power;
        this.tariff = tariff;
        this.cycle = cycle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
}
