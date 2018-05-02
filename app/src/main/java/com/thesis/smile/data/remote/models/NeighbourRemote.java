package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

public class NeighbourRemote {
    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String type;

    @Expose
    private String url;

    @Expose
    private boolean visible;

    @Expose
    private boolean active;

    @Expose
    private boolean blocked;

    public NeighbourRemote(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
