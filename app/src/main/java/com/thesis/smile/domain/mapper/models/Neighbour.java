package com.thesis.smile.domain.mapper.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Neighbour implements Parcelable {

    private String name;
    private String role;
    private String picture;
    private boolean visible;
    private boolean selectAll;

    public Neighbour(String name, String role, String picture, boolean visible) {
        this.name = name;
        this.role = role;
        this.picture = picture;
        this.visible = visible;
        this.selectAll = false;
    }

    public Neighbour(String name, boolean selectAll, boolean visible){
        this.name = name;
        this.selectAll = selectAll;
        this.visible = visible;
    }

    protected Neighbour(Parcel in) {
        name = in.readString();
        role = in.readString();
        picture = in.readString();
        visible = in.readByte() != 0;
        selectAll = in.readByte() != 0;
    }


    public static final Creator<Neighbour> CREATOR = new Creator<Neighbour>() {
        @Override
        public Neighbour createFromParcel(Parcel in) {
            return new Neighbour(in);
        }

        @Override
        public Neighbour[] newArray(int size) {
            return new Neighbour[size];
        }
    };

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

    public boolean isSelectAll() {
        return selectAll;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(role);
        parcel.writeString(picture);
        parcel.writeByte((byte) (visible ? 1 : 0));
        parcel.writeByte((byte) (selectAll ? 1 : 0));
    }
}
