package com.thesis.smile.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Neighbour implements Parcelable {

    private String id;
    private String name;
    private String type;
    private String url;
    private boolean visible;
    private boolean blocked;

    public Neighbour(){}
    public Neighbour(String id, String name, String type, String url, boolean visible) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
        this.visible = visible;
        this.blocked = false;
    }

    public Neighbour(String id, String name, boolean blocked, boolean visible){
        this.id = id;
        this.name = name;
        this.blocked = blocked;
        this.visible = visible;
    }

    protected Neighbour(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        url = in.readString();
        visible = in.readByte() != 0;
        blocked = in.readByte() != 0;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String picture) {
        this.url = picture;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(url);
        parcel.writeByte((byte) (visible ? 1 : 0));
        parcel.writeByte((byte) (blocked ? 1 : 0));
    }
}
