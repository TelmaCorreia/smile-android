package com.thesis.smile.iota.handler;

import android.content.Context;
import android.util.Log;

import com.thesis.smile.iota.requests.ApiRequest;
import com.thesis.smile.iota.requests.ReplayBundleRequest;
import com.thesis.smile.iota.responses.ApiResponse;
import com.thesis.smile.iota.responses.ReplayBundleResponse;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.iota.responses.error.NetworkErrorType;

import java.util.Arrays;

import jota.IotaAPI;
import jota.error.ArgumentException;

public class ReplayBundleRequestHandler extends IotaRequestHandler {
    public ReplayBundleRequestHandler(IotaAPI apiProxy, Context context) {
        super(apiProxy, context);
    }

    @Override
    public Class<? extends ApiRequest> getType() {
        return ReplayBundleRequest.class;
    }

    @Override
    public ApiResponse handle(ApiRequest request) {
        ApiResponse response;

        try {
            response = new ReplayBundleResponse(apiProxy.replayBundle(((ReplayBundleRequest) request).getHash(),
                    ((ReplayBundleRequest) request).getDepth(),
                    ((ReplayBundleRequest) request).getMinWeightMagnitude()));
        } catch (ArgumentException e) {
            NetworkError error = new NetworkError();
            error.setErrorType(NetworkErrorType.INVALID_HASH_ERROR);
            response = error;
        }

        if (response instanceof ReplayBundleResponse && Arrays.asList(((ReplayBundleResponse) response).getSuccessfully()).contains(true)) {
            Log.d(SendTransferRequestHandler.class.getCanonicalName(), "Reattach executed" );
        }else if (response instanceof NetworkError) {
            Log.e(SendTransferRequestHandler.class.getCanonicalName(), "Reattach failed" );
        }
        return response;
    }
}
