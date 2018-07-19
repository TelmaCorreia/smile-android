package com.thesis.smile.data.remote.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;

import org.threeten.bp.LocalDateTime;


public class TransactionRemote {

    @Expose
    private String id;
    @Expose
    private String from;
    @Expose
    private String to;
    @Expose
    private String url;
    @Expose
    private String type;
    @Expose
    private LocalDateTime date;
    @Expose
    private double priceKWH;
    @Expose
    private double quantity;
    @Expose
    private double total;
    @Expose
    private String address;
    @Expose
    private String state;

    public TransactionRemote(){}

    public TransactionRemote(String id, String from, String to, String url, String type, LocalDateTime date, double priceKWH, double quantity, double total, String address, String state) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.url = url;
        this.type = type;
        this.date = date;
        this.priceKWH = priceKWH;
        this.quantity = quantity;
        this.total = total;
        this.address = address;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
