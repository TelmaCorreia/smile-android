package com.thesis.smile.domain.models;


import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class Transaction implements Parcelable{

    private String name;
    private String clientName;
    private String supplierName;
    private String url;
    private String type;
    private LocalDateTime date;
    private String dateString;
    private double priceKWH;
    private int quantity;
    private double total;

    public Transaction(){}

    public Transaction(String name, String url, String type, LocalDateTime date, double priceKWH, int quantity, double total) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.date = date;
        this.priceKWH = priceKWH;
        this.quantity = quantity;
        this.total = total;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        this.dateString = date.format(formatter);
    }

    protected Transaction(Parcel in) {
        name = in.readString();
        clientName = in.readString();
        supplierName = in.readString();
        url = in.readString();
        type = in.readString();
        dateString = in.readString();
        priceKWH = in.readDouble();
        quantity = in.readInt();
        total = in.readDouble();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(clientName);
        parcel.writeString(supplierName);
        parcel.writeString(url);
        parcel.writeString(type);
        parcel.writeString(dateString);
        parcel.writeDouble(priceKWH);
        parcel.writeInt(quantity);
        parcel.writeDouble(total);
    }
}
