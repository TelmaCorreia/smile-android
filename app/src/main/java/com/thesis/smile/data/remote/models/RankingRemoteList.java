package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RankingRemoteList {

    @Expose
    @SerializedName("rankings")
    private List<RankingRemote> rankings;

    public RankingRemoteList(){}

    public RankingRemoteList(List<RankingRemote> rankings){
        this.rankings = rankings;
    }

    public List<RankingRemote> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingRemote> rankings) {
        this.rankings = rankings;
    }

}
