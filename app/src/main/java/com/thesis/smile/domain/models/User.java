package com.thesis.smile.domain.models;

public class User {

    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String url;
    
    private String type;

    private boolean visible;

    private boolean active;

    private boolean manual;

    private String cons_smart_meter_id;

    private String prod_smart_meter_id;

    private String encryptedSeed;

    private EnergyParams energyParams;

    public User(){}

    public User(String firstName, String lastName, String email, String type, boolean visible) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.type = type;
        this.visible=visible;
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

    public void setEnergyParams(EnergyParams energyParamsRemote) {
        this.energyParams = energyParamsRemote;
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
}
