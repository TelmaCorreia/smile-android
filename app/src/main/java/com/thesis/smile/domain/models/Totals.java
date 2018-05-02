package com.thesis.smile.domain.models;

public class Totals {

    private double totalSold;
    private double totalBought;

    public Totals() {
    }

    public Totals(double totalSold, double totalBought) {
        this.totalSold = totalSold;
        this.totalBought = totalBought;
    }

    public double getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(double totalSold) {
        this.totalSold = totalSold;
    }

    public double getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(double totalBought) {
        this.totalBought = totalBought;
    }
}


