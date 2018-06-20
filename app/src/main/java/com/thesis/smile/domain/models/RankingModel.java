package com.thesis.smile.domain.models;


import java.util.List;

public class RankingModel {

    private int position;
    private List<RankingModelList> rankings;

    public RankingModel(){

    }

    public RankingModel(int position, List<RankingModelList> rankings) {
        this.position = position;
        this.rankings = rankings;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<RankingModelList> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingModelList> rankings) {
        this.rankings = rankings;
    }
}
