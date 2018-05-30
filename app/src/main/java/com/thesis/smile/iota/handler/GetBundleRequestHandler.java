package com.thesis.smile.iota.handler;

import android.content.Context;

import com.thesis.smile.iota.requests.ApiRequest;
import com.thesis.smile.iota.requests.GetBundleRequest;
import com.thesis.smile.iota.responses.ApiResponse;
import com.thesis.smile.iota.responses.GetBundleResponse;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.iota.responses.error.NetworkErrorType;

import jota.IotaAPI;
import jota.error.ArgumentException;

public class GetBundleRequestHandler extends IotaRequestHandler {
    public GetBundleRequestHandler(IotaAPI apiProxy, Context context) {
        super(apiProxy, context);
    }

    @Override
    public Class<? extends ApiRequest> getType() {
        return GetBundleRequest.class;
    }

    @Override
    public ApiResponse handle(ApiRequest request) {
        ApiResponse response;
        try {
            response =  new GetBundleResponse(apiProxy.getBundle(((GetBundleRequest) request).getTransaction()));
        } catch (ArgumentException e) {
            NetworkError error = new NetworkError();
            error.setErrorType(NetworkErrorType.INVALID_HASH_ERROR);
            response = error;
        }
        return response;
    }
}
