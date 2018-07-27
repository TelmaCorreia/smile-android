package com.thesis.smile.data.remote.models.request;

import com.google.gson.annotations.Expose;
import com.thesis.smile.data.remote.models.EnergyParamsRemote;

import java.io.File;

public class RegisterRequest{

    @Expose
    private String email;

    @Expose
    private String password;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String type;

    @Expose
    private boolean visible;

    @Expose
    private boolean manual;

    @Expose
    private String encryptedSeed;

    @Expose
    private String firebaseToken;

    @Expose
    private EnergyParamsRemote energyParams;

    @Expose
    private String smartMeterId;

    @Expose
    private String filePath;

    private File picture;

    public RegisterRequest(){}

    public RegisterRequest(String firstName, String lastName, String email, String password,
                           String type, boolean visible, boolean manual, String encryptedSeed, String firebaseToken, EnergyParamsRemote energyParamsRemote, String smartMeterId) {
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
        this.smartMeterId = smartMeterId;
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

    public String getSmartMeterId() {
        return smartMeterId;
    }

    public void setSmartMeterId(String smartMeterId) {
        this.smartMeterId = smartMeterId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
