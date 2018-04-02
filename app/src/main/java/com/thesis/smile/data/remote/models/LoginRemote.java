package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thesis.smile.data.remote.models.User;

public class LoginRemote {

    @SerializedName("Token")
    @Expose
    private String token;

    @SerializedName("User")
    @Expose
    private User userRemote;

    public LoginRemote(){}

    public User getUserRemote() {
        return this.userRemote;
    }

    public void setUserRemote(User userRemote){
        this.userRemote = userRemote;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
