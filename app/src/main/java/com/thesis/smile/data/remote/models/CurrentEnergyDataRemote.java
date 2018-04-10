package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

import java.util.Map;

public class CurrentEnergyDataRemote {

    @Expose
    private double production;
    @Expose
    private double consumption;
    @Expose
    private double batteryLevel;
    @Expose
    private double batteryKWH;
    @Expose
    private double totalBought;
    @Expose
    private double totalSold;
    @Expose
    private double totalSolarEnergy;

    public CurrentEnergyDataRemote(){}
    public CurrentEnergyDataRemote(double production, double consumption, double batteryLevel, double batteryKWH, double totalBought, double totalSold, double totalSolarEnergy) {
        this.production = production;
        this.consumption = consumption;
        this.batteryLevel = batteryLevel;
        this.batteryKWH = batteryKWH;
        this.totalBought = totalBought;
        this.totalSold = totalSold;
        this.totalSolarEnergy = totalSolarEnergy;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getBatteryKWH() {
        return batteryKWH;
    }

    public void setBatteryKWH(double batteryKWH) {
        this.batteryKWH = batteryKWH;
    }

    public double getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(double totalBought) {
        this.totalBought = totalBought;
    }

    public double getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(double totalSold) {
        this.totalSold = totalSold;
    }

    public double getTotalSolarEnergy() {
        return totalSolarEnergy;
    }

    public void setTotalSolarEnergy(double totalSolarEnergy) {
        this.totalSolarEnergy = totalSolarEnergy;
    }
}
