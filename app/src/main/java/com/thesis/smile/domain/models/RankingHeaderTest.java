package com.thesis.smile.domain.models;


import java.util.List;

public class RankingHeaderTest {

    private String title;
    private List<Ranking> rankings;

    public RankingHeaderTest(){

    }

    public RankingHeaderTest(String title, List<Ranking> rankings) {
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
