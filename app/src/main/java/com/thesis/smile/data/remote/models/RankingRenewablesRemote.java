package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankingRenewablesRemote {

    @Expose
    @SerializedName("rankingRenewable")
    private List<RankingRenewableRemote> rankingRenewableRemotes;

    public RankingRenewablesRemote(){}

    public List<RankingRenewableRemote> getRankingRenewableRemotes() {
        return rankingRenewableRemotes;
    }

    public void setRankingRenewableRemotes(List<RankingRenewableRemote> rankingRenewableRemotes) {
        this.rankingRenewableRemotes = rankingRenewableRemotes;
    }
}
