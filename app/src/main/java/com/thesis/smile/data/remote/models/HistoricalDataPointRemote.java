package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

public class HistoricalDataPointRemote {

    @Expose
    private String title;

    @Expose
    private double totalConsumption;

    @Expose
    private double totalProduction;

    @Expose
    private double energySurplus;

    @Expose
    private double energySurplusNeighbours;

    @Expose
    private double energySurplusNotUsed;

    @Expose
    private double energyAutoConsumptionTotal;

    @Expose
    private double energyAutoConsumptionBattery;

    @Expose
    private double getEnergyAutoConsumptionPanels;

    @Expose
    private double energyBought;

    @Expose
    private double energyBoughtNeighbours;

    @Expose
    private double energyBoughtEem;

    public HistoricalDataPointRemote(){}

    public HistoricalDataPointRemote(String title, double totalConsumption, double totalProduction, double energySurplus, double energySurplusNeighbours, double energySurplusNotUsed, double energyAutoConsumptionTotal, double energyAutoConsumptionBattery, double getEnergyAutoConsumptionPanels, double energyBought, double energyBoughtNeighbours, double energyBoughtEem) {
        this.title = title;
        this.totalConsumption = totalConsumption;
        this.totalProduction = totalProduction;
        this.energySurplus = energySurplus;
        this.energySurplusNeighbours = energySurplusNeighbours;
        this.energySurplusNotUsed = energySurplusNotUsed;
        this.energyAutoConsumptionTotal = energyAutoConsumptionTotal;
        this.energyAutoConsumptionBattery = energyAutoConsumptionBattery;
        this.getEnergyAutoConsumptionPanels = getEnergyAutoConsumptionPanels;
        this.energyBought = energyBought;
        this.energyBoughtNeighbours = energyBoughtNeighbours;
        this.energyBoughtEem = energyBoughtEem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getTotalProduction() {
        return totalProduction;
    }

    public void setTotalProduction(double totalProduction) {
        this.totalProduction = totalProduction;
    }

    public double getEnergySurplus() {
        return energySurplus;
    }

    public void setEnergySurplus(double energySurplus) {
        this.energySurplus = energySurplus;
    }

    public double getEnergySurplusNeighbours() {
        return energySurplusNeighbours;
    }

    public void setEnergySurplusNeighbours(double energySurplusNeighbours) {
        this.energySurplusNeighbours = energySurplusNeighbours;
    }

    public double getEnergySurplusNotUsed() {
        return energySurplusNotUsed;
    }

    public void setEnergySurplusNotUsed(double energySurplusNotUsed) {
        this.energySurplusNotUsed = energySurplusNotUsed;
    }

    public double getEnergyAutoConsumptionTotal() {
        return energyAutoConsumptionTotal;
    }

    public void setEnergyAutoConsumptionTotal(double energyAutoConsumptionTotal) {
        this.energyAutoConsumptionTotal = energyAutoConsumptionTotal;
    }

    public double getEnergyAutoConsumptionBattery() {
        return energyAutoConsumptionBattery;
    }

    public void setEnergyAutoConsumptionBattery(double energyAutoConsumptionBattery) {
        this.energyAutoConsumptionBattery = energyAutoConsumptionBattery;
    }

    public double getGetEnergyAutoConsumptionPanels() {
        return getEnergyAutoConsumptionPanels;
    }

    public void setGetEnergyAutoConsumptionPanels(double getEnergyAutoConsumptionPanels) {
        this.getEnergyAutoConsumptionPanels = getEnergyAutoConsumptionPanels;
    }

    public double getEnergyBought() {
        return energyBought;
    }

    public void setEnergyBought(double energyBought) {
        this.energyBought = energyBought;
    }

    public double getEnergyBoughtNeighbours() {
        return energyBoughtNeighbours;
    }

    public void setEnergyBoughtNeighbours(double energyBoughtNeighbours) {
        this.energyBoughtNeighbours = energyBoughtNeighbours;
    }

    public double getEnergyBoughtEem() {
        return energyBoughtEem;
    }

    public void setEnergyBoughtEem(double energyBoughtEem) {
        this.energyBoughtEem = energyBoughtEem;
    }
}
