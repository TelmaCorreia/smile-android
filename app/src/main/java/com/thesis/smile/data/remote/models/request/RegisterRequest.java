package com.thesis.smile.data.remote.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thesis.smile.data.remote.models.EnergyParams;

import java.io.Serializable;

public class RegisterRequest{

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("visible")
    private boolean visible;

    @Expose
    @SerializedName("energyParams")
    private EnergyParams energyParams;

    public RegisterRequest(){}

    public RegisterRequest(String firstName, String lastName, String email, String password,
                           String type, boolean visible, EnergyParams energyParams) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.visible = visible;
        this.energyParams = energyParams;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public EnergyParams getEnergyParams() {
        return energyParams;
    }

    public void setEnergyParams(EnergyParams energyParams) {
        this.energyParams = energyParams;
    }
}
