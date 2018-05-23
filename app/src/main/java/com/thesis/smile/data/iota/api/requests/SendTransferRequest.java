package com.thesis.smile.data.iota.api.requests;

import com.thesis.smile.SmileApp;

import java.util.ArrayList;
import java.util.List;

import jota.model.Transfer;

public class SendTransferRequest extends ApiRequest {

    private String seed;
    private int security = 2;
    private String address = "";
    private List<String> addresses;
    private String value = "";
    private String message = "";
    private String tag = "";
    private int minWeightMagnitude = 14;
    private int depth = 9;

    public SendTransferRequest(String address, String value, String message, String tag) {
        this.seed = String.valueOf(SmileApp.seed);
        this.address = address;
        this.value = value;
        this.message = message;
        this.tag = tag;
    }

    public SendTransferRequest(List<String> addresses, String value, String message, String tag) {
        this.seed = String.valueOf(SmileApp.seed);
        this.addresses = addresses;
        this.value = value;
        this.message = message;
        this.tag = tag;
    }

    public List<Transfer> prepareTransfer() {
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer(address, Long.valueOf(value), message, tag));
        return transfers;
    }

    public List<Transfer> prepareTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < addresses.size(); i++) {
            transfers.add(new Transfer(addresses.get(i), Long.valueOf(value), message, tag));
        }
        return transfers;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getMinWeightMagnitude() {
        return minWeightMagnitude;
    }

    public void setMinWeightMagnitude(int minWeightMagnitude) {
        this.minWeightMagnitude = minWeightMagnitude;
    }


    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}

