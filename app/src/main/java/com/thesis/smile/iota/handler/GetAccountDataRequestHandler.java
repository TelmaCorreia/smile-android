package com.thesis.smile.iota.handler;

import android.content.Context;

import com.thesis.smile.iota.requests.ApiRequest;
import com.thesis.smile.iota.requests.GetAccountDataRequest;
import com.thesis.smile.iota.responses.ApiResponse;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.iota.responses.error.NetworkError;

import jota.IotaAPI;
import jota.error.ArgumentException;

public class GetAccountDataRequestHandler extends IotaRequestHandler {

    public GetAccountDataRequestHandler(IotaAPI iotaApi, Context context) {
        super(iotaApi, context);
    }

    @Override
    public Class<? extends ApiRequest> getType() {
        return GetAccountDataRequest.class;
    }

    @Override
    public ApiResponse handle(ApiRequest request) {
        ApiResponse response;

        try {
            response = new GetAccountDataResponse(apiProxy.getAccountData(((GetAccountDataRequest) request).getSeed(),
                    ((GetAccountDataRequest) request).getSecurity(),
                    ((GetAccountDataRequest) request).getIndex(),
                    ((GetAccountDataRequest) request).isChecksum(),
                    ((GetAccountDataRequest) request).getTotal(),
                    ((GetAccountDataRequest) request).isReturnAll(),
                    ((GetAccountDataRequest) request).getStart(),
                    ((GetAccountDataRequest) request).getEnd(),
                    ((GetAccountDataRequest) request).isInclusionState(),
                    ((GetAccountDataRequest) request).getThreshold()));
        } catch (ArgumentException e) {
            response = new NetworkError();
        }
        return response;
    }
}
