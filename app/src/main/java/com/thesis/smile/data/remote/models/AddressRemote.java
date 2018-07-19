package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

public class AddressRemote {

    @Expose
    private String address;
    @Expose
    private String bundle;
    @Expose
    private String user;
    @Expose
    private String transaction;

    public AddressRemote(){}

    public AddressRemote(String address, String bundle, String user, String transaction) {
        this.address = address;
        this.bundle = bundle;
        this.user = user;
        this.transaction = transaction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
