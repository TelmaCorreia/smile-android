package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

public class UsersRemote {

    @Expose
    private String name;

    @Expose
    private String email;

    @Expose
    private String token;

    @Expose
    private boolean on;

    @Expose
    private String lastDataTimestamp;

    @Expose
    private String smart_meter_id;


    public UsersRemote() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastDataTimestamp() {
        return lastDataTimestamp;
    }

    public void setLastDataTimestamp(String lastDataTimestamp) {
        this.lastDataTimestamp = lastDataTimestamp;
    }

    public String getSmart_meter_id() {
        return smart_meter_id;
    }

    public void setSmart_meter_id(String smart_meter_id) {
        this.smart_meter_id = smart_meter_id;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
