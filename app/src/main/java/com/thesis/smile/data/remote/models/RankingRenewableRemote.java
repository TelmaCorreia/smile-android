package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RankingRenewableRemote {

    @Expose
    private int quantity;
    @Expose
    @SerializedName("rankingList")
    private List<RankingRemoteList> rankings;

    public RankingRenewableRemote(){}

    public RankingRenewableRemote(int quantity, List<RankingRemoteList> rankings){
        this.quantity = quantity;
        this.rankings = rankings;
    }

    public List<RankingRemoteList> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingRemoteList> rankings) {
        this.rankings = rankings;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
