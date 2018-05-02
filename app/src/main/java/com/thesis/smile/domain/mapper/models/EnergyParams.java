package com.thesis.smile.domain.mapper.models;

public class EnergyParams {

    private String category;
    
    private String power;
    
    private String tariff;
    
    private String cycle;

    public EnergyParams(){}
    public EnergyParams(String category, String power, String tariff, String cycle) {
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
