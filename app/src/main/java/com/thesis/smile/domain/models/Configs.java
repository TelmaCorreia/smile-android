package com.thesis.smile.domain.models;

import com.google.gson.annotations.Expose;

import java.util.Map;

public class Configs {

    
    private Map<Integer, String> categories;

    
    private Map<Integer, String> power;

    
    private Map<Integer, String> tariff;

    
    private Map<Integer, String> cycle;

    
    private Map<Integer, String> userType;

    public Configs(){}
    public Configs(Map<Integer, String> categories, Map<Integer, String> power, Map<Integer, String> tariff, Map<Integer, String> cycle, Map<Integer, String> userType) {
        this.categories = categories;
        this.power = power;
        this.tariff = tariff;
        this.cycle = cycle;
        this.userType = userType;
    }

    public Map<Integer, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<Integer, String> categories) {
        this.categories = categories;
    }

    public Map<Integer, String> getPower() {
        return power;
    }

    public void setPower(Map<Integer, String> power) {
        this.power = power;
    }

    public Map<Integer, String> getTariff() {
        return tariff;
    }

    public void setTariff(Map<Integer, String> tariff) {
        this.tariff = tariff;
    }

    public Map<Integer, String> getCycle() {
        return cycle;
    }

    public void setCycle(Map<Integer, String> cycle) {
        this.cycle = cycle;
    }

    public Map<Integer, String> getUserType() {
        return userType;
    }

    public void setUserType(Map<Integer, String> userType) {
        this.userType = userType;
    }
}
