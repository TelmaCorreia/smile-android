package com.thesis.smile.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;


public class Ranking implements Parcelable {

    private String name;
    private String position;
    private String description;
    private String picture;
    private LocalDate date;

    public Ranking(){}

    public Ranking(String name, String position, String description, String picture, LocalDate date) {
        this.name = name;
        this.position = position;
        this.description = description;
        this.picture = picture;
        this.date = date;
    }


    protected Ranking(Parcel in) {
        name = in.readString();
        position = in.readString();
        description = in.readString();
        picture = in.readString();
    }

    public static final Creator<Ranking> CREATOR = new Creator<Ranking>() {
        @Override
        public Ranking createFromParcel(Parcel in) {
            return new Ranking(in);
        }

        @Override
        public Ranking[] newArray(int size) {
            return new Ranking[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(position);
        parcel.writeString(description);
        parcel.writeString(picture);
    }
}
