package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RankingRenewableRemote {

    @Expose
    private int position;
    @Expose
    @SerializedName("rankingList")
    private List<RankingRemoteList> rankings;

    public RankingRenewableRemote(){}

    public RankingRenewableRemote(int position, List<RankingRemoteList> rankings){
        this.position = position;
        this.rankings = rankings;
    }

    public List<RankingRemoteList> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingRemoteList> rankings) {
        this.rankings = rankings;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
