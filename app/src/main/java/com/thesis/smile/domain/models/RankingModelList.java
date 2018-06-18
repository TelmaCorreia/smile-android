package com.thesis.smile.domain.models;


import java.util.List;

public class RankingModelList {

    private List<Ranking> rankings;

    public RankingModelList(){

    }

    public RankingModelList( List<Ranking> rankings) {
        this.rankings = rankings;
    }


    public List<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
    }
}
