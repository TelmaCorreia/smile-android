package com.thesis.smile.domain.models_iota;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class Address implements Parcelable {

    public static final Creator<Transfer> CREATOR = new Creator<Transfer>() {
        @Override
        public Transfer createFromParcel(Parcel in) {
            return new Transfer(in);
        }

        @Override
        public Transfer[] newArray(int size) {
            return new Transfer[size];
        }
    };
    private String address;
    private Boolean used;


    public Address(String address, boolean used) {
        this.address = address;
        this.used = used;
    }

    public Address(Parcel in) {
        address = in.readString();
        used = in.readInt() != 0;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeInt((used != null ? used : false) ? 1 : 0);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public boolean equals(Object object) {
        return this.getAddress().equals(((Address) object).getAddress());
    }
}
