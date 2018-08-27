package com.thesis.smile.domain.models;

public class Users {

    private String name;

    private String email;

    private String token;

    private boolean on;

    private String lastDataTimestamp;

    private String smart_meter_id;

    public Users(){

    }

    public Users(String name, String email, String token, boolean on, String lastDataTimestamp, String smart_meter_id) {
        this.name = name;
        this.email = email;
        this.token = token;
        this.on = on;
        this.lastDataTimestamp = lastDataTimestamp;
        this.smart_meter_id = smart_meter_id;
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
