package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

import org.threeten.bp.LocalDate;

public class RankingRemote {

    @Expose
    private String name;

    @Expose
    private String position;

    @Expose
    private String description;

    @Expose
    private String picture;

    @Expose
    private LocalDate date;

    public RankingRemote(){}

    public RankingRemote(String name, String position, String description, String picture, LocalDate date) {
        this.name = name;
        this.position = position;
        this.description = description;
        this.picture = picture;
        this.date = date;
    }

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
}
