package com.thesis.smile.iota.requests;

import com.thesis.smile.SmileApp;

public class GetAccountDataRequest extends ApiRequest {

    private String seed;
    private int security = 2;
    private int index = 0;
    private boolean checksum = true;
    private int total = 0;
    private boolean returnAll = true;
    private int start = 0;
    private int end = 0;
    private boolean inclusionState = true;
    private long threshold = 0;


    public GetAccountDataRequest() {
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isChecksum() {
        return checksum;
    }

    public void setChecksum(boolean checksum) {
        this.checksum = checksum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isReturnAll() {
        return returnAll;
    }

    public void setReturnAll(boolean returnAll) {
        this.returnAll = returnAll;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isInclusionState() {
        return inclusionState;
    }

    public void setInclusionState(boolean inclusionState) {
        this.inclusionState = inclusionState;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }
}
