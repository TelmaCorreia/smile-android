package com.thesis.smile.domain.models;


import java.util.List;

public class RankingModel {

    private int quantity;
    private List<RankingModelList> rankings;

    public RankingModel(){

    }

    public RankingModel(int quantity, List<RankingModelList> rankings) {
        this.quantity = quantity;
        this.rankings = rankings;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<RankingModelList> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingModelList> rankings) {
        this.rankings = rankings;
    }
}
