package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;

import java.util.List;


public class RankingRenewableRemote {

    @Expose
    private String title;
    @Expose
    private List<RankingRemote> rankings;

    public RankingRenewableRemote(){}

    public RankingRenewableRemote(String title, List<RankingRemote> rankings){
        this.title = title;
        this.rankings = rankings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RankingRemote> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingRemote> rankings) {
        this.rankings = rankings;
    }
}
