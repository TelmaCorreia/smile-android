package com.thesis.smile.data.iota.api.responses;

public class SendTransferResponse extends ApiResponse {

    private Boolean[] successfully;

    public SendTransferResponse(jota.dto.response.SendTransferResponse apiResponse) {
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
