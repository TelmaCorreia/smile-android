package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersListRemote {

    @Expose
    @SerializedName("users")
    private List<UsersRemote> users;

    public UsersListRemote(){}

    public List<UsersRemote> getUsers() {
        return users;
    }

    public void setUsers(List<UsersRemote> users) {
        this.users = users;
    }
}
