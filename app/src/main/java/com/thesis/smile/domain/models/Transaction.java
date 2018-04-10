package com.thesis.smile.domain.models;


import java.time.LocalDateTime;

public class Transaction {

    private String name;
    private String url;
    private LocalDateTime date;
    private double priceKWH;
    private int quantity;
    private double total;

    public Transaction(){}

    public Transaction(String name, String url, LocalDateTime date, double priceKWH, int quantity, double total) {
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
