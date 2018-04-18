package com.thesis.smile.domain.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class NeighbourHeader extends ExpandableGroup<Neighbour> {

    private String title;
    private String subtitle;
    private List<Neighbour> neighbours;

    public NeighbourHeader(String title, String subtitle, List<Neighbour> neighbours) {
        super(title, neighbours);
        this.title = title;
        this.subtitle = subtitle;
        this.neighbours = neighbours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

}
