package com.thesis.smile.domain.models;

public class Address {
    private String address;
    private String bundle;
    private String user;
    private String transaction;

    public Address(String address, String user) {
        this.address=address;
        this.user=user;
    }

    public Address(String address) {
        this.address=address;
    }
    public Address(){}

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
