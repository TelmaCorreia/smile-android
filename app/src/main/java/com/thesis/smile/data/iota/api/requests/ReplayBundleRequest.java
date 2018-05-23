package com.thesis.smile.data.iota.api.requests;

public class ReplayBundleRequest extends ApiRequest {

    private String hash;
    private int minWeightMagnitude = 14;
    private int depth = 9;

    public ReplayBundleRequest(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getMinWeightMagnitude() {
        return minWeightMagnitude;
    }

    public void setMinWeightMagnitude(int minWeightMagnitude) {
        this.minWeightMagnitude = minWeightMagnitude;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
