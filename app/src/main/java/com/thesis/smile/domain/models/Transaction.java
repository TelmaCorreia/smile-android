package com.thesis.smile.domain.models;


import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class Transaction implements Parcelable{

    private String id;
    private String from;
    private String to;
    private String url;
    private String type;
    private LocalDateTime date;
    private double priceKWH;
    private double quantity;
    private double total;
    private String dateString;

    public Transaction(){}

    public Transaction(String id, String from, String to, String url, String type, LocalDateTime date, double priceKWH, double quantity, double total) {
        this.id = id;
        this.from = from;
        this.to = to;
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
        id = in.readString();
        from = in.readString();
        to = in.readString();
        url = in.readString();
        type = in.readString();
        priceKWH = in.readDouble();
        quantity = in.readDouble();
        total = in.readDouble();
        dateString = in.readString();
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

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeString(url);
        parcel.writeString(type);
        parcel.writeDouble(priceKWH);
        parcel.writeDouble(quantity);
        parcel.writeDouble(total);
        parcel.writeString(dateString);
    }
}
