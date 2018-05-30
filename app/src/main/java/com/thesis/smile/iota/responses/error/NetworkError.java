package com.thesis.smile.iota.responses.error;

import com.thesis.smile.iota.responses.ApiResponse;

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
