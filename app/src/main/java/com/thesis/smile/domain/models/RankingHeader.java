package com.thesis.smile.domain.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RankingHeader  extends ExpandableGroup<Ranking> {

    private String title;
    private List<Ranking> rankings;

    public RankingHeader(String title, List<Ranking> rankings) {
        super(title, rankings);
        this.title = title;
        this.rankings = rankings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
