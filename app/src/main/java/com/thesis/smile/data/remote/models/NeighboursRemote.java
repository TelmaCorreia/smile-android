package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NeighboursRemote {

    @Expose
    @SerializedName("neighbours")
    private List<NeighbourRemote> neighbours;

    public NeighboursRemote(){}

    public NeighboursRemote(List<NeighbourRemote> neighbours){
        this.neighbours = neighbours;
    }

    public List<NeighbourRemote> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<NeighbourRemote> neighbours) {
        this.neighbours = neighbours;
    }
}
