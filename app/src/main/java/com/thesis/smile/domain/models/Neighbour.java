package com.thesis.smile.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Neighbour implements Parcelable {

    private String name;
    private String role;
    private String picture;
    private boolean visible;

    public Neighbour(String name, String role, String picture, boolean visible) {
        this.name = name;
        this.role = role;
        this.picture = picture;
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
