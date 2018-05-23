package com.thesis.smile.data.iota.api.responses.error;

import com.thesis.smile.data.iota.api.responses.ApiResponse;

public class NetworkError extends ApiResponse {

    private NetworkErrorType errorType;

    public NetworkError() {
        errorType = NetworkErrorType.REMOTE_NODE_ERROR;
    }

    public NetworkErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(NetworkErrorType errorType) {
        this.errorType = errorType;
    }
}
