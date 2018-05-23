package com.thesis.smile.data.iota.api.responses;

public class ReplayBundleResponse extends ApiResponse {

    private Boolean[] successfully;

    public ReplayBundleResponse(jota.dto.response.ReplayBundleResponse apiResponse) {
        successfully = apiResponse.getSuccessfully();
        setDuration(apiResponse.getDuration());
    }

    public Boolean[] getSuccessfully() {
        return successfully;
    }

    public void setSuccessfully(Boolean[] successfully) {
        this.successfully = successfully;
    }
}