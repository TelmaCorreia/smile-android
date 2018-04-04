package com.thesis.smile.data.remote.models;

public class EnergyParams {

    private int category;
    private int power;
    private int tariff;
    private int cycle;

    public EnergyParams(int category, int power, int tariff, int cycle) {
        this.category = category;
        this.power = power;
        this.tariff = tariff;
        this.cycle = cycle;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getTariff() {
        return tariff;
    }

    public void setTariff(int tariff) {
        this.tariff = tariff;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
}
