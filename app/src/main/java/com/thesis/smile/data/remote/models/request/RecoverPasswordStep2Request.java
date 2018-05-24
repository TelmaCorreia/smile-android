package com.thesis.smile.data.remote.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecoverPasswordStep2Request {

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("pin")
    private String password;


    public RecoverPasswordStep2Request(){}

    public RecoverPasswordStep2Request(String email, String password) {
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
