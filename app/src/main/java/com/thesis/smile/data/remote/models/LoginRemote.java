package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRemote {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private UserRemote userRemote;

    public LoginRemote(){}

    public UserRemote getUserRemote() {
        return this.userRemote;
    }

    public void setUserRemote(UserRemote userRemote){
        this.userRemote = userRemote;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
