package com.thesis.smile.data.remote.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thesis.smile.data.remote.models.EnergyParamsRemote;

import java.io.File;

public class RecoverPasswordStep1Request {

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;


    public RecoverPasswordStep1Request(){}

    public RecoverPasswordStep1Request(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
