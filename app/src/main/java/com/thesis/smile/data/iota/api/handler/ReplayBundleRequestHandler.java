package com.thesis.smile.data.iota.api.handler;

import android.content.Context;
import android.util.Log;

import com.thesis.smile.data.iota.api.requests.ApiRequest;
import com.thesis.smile.data.iota.api.requests.ReplayBundleRequest;
import com.thesis.smile.data.iota.api.responses.ApiResponse;
import com.thesis.smile.data.iota.api.responses.ReplayBundleResponse;
import com.thesis.smile.data.iota.api.responses.error.NetworkError;
import com.thesis.smile.data.iota.api.responses.error.NetworkErrorType;
import com.thesis.smile.utils.iota.Utils;

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
        int notificationId = Utils.createNewID();

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
