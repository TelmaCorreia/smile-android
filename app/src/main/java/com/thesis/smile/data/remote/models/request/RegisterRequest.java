package com.thesis.smile.data.remote.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thesis.smile.data.remote.models.EnergyParamsRemote;

import java.io.File;

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
    @SerializedName("manual")
    private boolean manual;

    @Expose
    @SerializedName("encryptedSeed")
    private String encryptedSeed;

    @Expose
    @SerializedName("firebaseToken")
    private String firebaseToken;

    @Expose
    @SerializedName("energyParams")
    private EnergyParamsRemote energyParams;

    private File picture;

    public RegisterRequest(){}

    public RegisterRequest(String firstName, String lastName, String email, String password,
                           String type, boolean visible, boolean manual, String encryptedSeed, String firebaseToken, EnergyParamsRemote energyParamsRemote) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.visible = visible;
        this.manual = manual;
        this.encryptedSeed = encryptedSeed;
        this.firebaseToken = firebaseToken;
        this.energyParams = energyParamsRemote;
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

    public EnergyParamsRemote getEnergyParams() {
        return energyParams;
    }

    public void setEnergyParams(EnergyParamsRemote energyParamsRemote) {
        this.energyParams = energyParamsRemote;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public String getEncryptedSeed() {
        return encryptedSeed;
    }

    public void setEncryptedSeed(String encryptedSeed) {
        this.encryptedSeed = encryptedSeed;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
