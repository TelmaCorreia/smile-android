package com.thesis.smile.iota.requests;

public class GetBundleRequest extends ApiRequest {

    private String transaction = "";

    public GetBundleRequest(String transaction) {
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}