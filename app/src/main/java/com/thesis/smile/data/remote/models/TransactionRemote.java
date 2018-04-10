package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class TransactionRemote {

    @Expose
    private String name;
    @Expose
    private String url;
    @Expose
    private LocalDateTime date;
    @Expose
    private double priceKWH;
    @Expose
    private int quantity;
    @Expose
    private double total;

    public TransactionRemote(){}

    public TransactionRemote(String name, String url, LocalDateTime date, double priceKWH, int quantity, double total) {
        this.name = name;
        this.url = url;
        this.date = date;
        this.priceKWH = priceKWH;
        this.quantity = quantity;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPriceKWH() {
        return priceKWH;
    }

    public void setPriceKWH(double priceKWH) {
        this.priceKWH = priceKWH;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
