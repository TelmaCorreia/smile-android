package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRemote{

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String email;

    @Expose
    private String url;

    @Expose
    private String type;

    @Expose
    private boolean visible;

    @Expose
    private boolean active;

    @Expose
    private String cons_smart_meter_id;

    @Expose
    private String prod_smart_meter_id;

    @Expose
    private EnergyParams energyParams;

    public UserRemote(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCons_smart_meter_id() {
        return cons_smart_meter_id;
    }

    public void setCons_smart_meter_id(String cons_smart_meter_id) {
        this.cons_smart_meter_id = cons_smart_meter_id;
    }

    public String getProd_smart_meter_id() {
        return prod_smart_meter_id;
    }

    public void setProd_smart_meter_id(String prod_smart_meter_id) {
        this.prod_smart_meter_id = prod_smart_meter_id;
    }

    public EnergyParams getEnergyParams() {
        return energyParams;
    }

    public void setEnergyParams(EnergyParams energyParams) {
        this.energyParams = energyParams;
    }
}
