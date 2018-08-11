package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

import java.util.List;

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
    @Expose
    private List<Integer> boughtList;
    @Expose
    private List<Integer> soldList;
    @Expose
    private List<Double> productionList;
    @Expose
    private List<Double> consumptionList;

    public CurrentEnergyDataRemote(){}
    public CurrentEnergyDataRemote(double production, double consumption, double batteryLevel, double batteryKWH, double totalBought, double totalSold, double totalSolarEnergy, List<Integer> boughtList, List<Integer> soldList, List<Double> productionList, List<Double> consumptionList) {
        this.production = production;
        this.consumption = consumption;
        this.batteryLevel = batteryLevel;
        this.batteryKWH = batteryKWH;
        this.totalBought = totalBought;
        this.totalSold = totalSold;
        this.totalSolarEnergy = totalSolarEnergy;
        this.boughtList = boughtList;
        this.soldList = soldList;
        this.productionList = productionList;
        this.consumptionList = consumptionList;
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

    public List<Integer> getBoughtList() {
        return boughtList;
    }

    public void setBoughtList(List<Integer> boughtList) {
        this.boughtList = boughtList;
    }

    public List<Integer> getSoldList() {
        return soldList;
    }

    public void setSoldList(List<Integer> soldList) {
        this.soldList = soldList;
    }

    public List<Double> getProductionList() {
        return productionList;
    }

    public void setProductionList(List<Double> productionList) {
        this.productionList = productionList;
    }

    public List<Double> getConsumptionList() {
        return consumptionList;
    }

    public void setConsumptionList(List<Double> consumptionList) {
        this.consumptionList = consumptionList;
    }
}
