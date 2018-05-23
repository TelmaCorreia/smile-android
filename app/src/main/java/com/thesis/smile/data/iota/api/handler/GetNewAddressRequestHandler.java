package com.thesis.smile.data.iota.api.handler;

import android.content.Context;

import com.thesis.smile.data.iota.api.requests.ApiRequest;
import com.thesis.smile.data.iota.api.requests.GetNewAddressRequest;
import com.thesis.smile.data.iota.api.responses.ApiResponse;
import com.thesis.smile.data.iota.api.responses.GetNewAddressResponse;
import com.thesis.smile.data.iota.api.responses.error.NetworkError;

import jota.IotaAPI;
import jota.error.ArgumentException;

public class GetNewAddressRequestHandler extends IotaRequestHandler {
    public GetNewAddressRequestHandler(IotaAPI apiProxy, Context context) {
        super(apiProxy, context);
    }

    @Override
    public Class<? extends ApiRequest> getType() {
        return GetNewAddressRequest.class;
    }

    @Override
    public ApiResponse handle(ApiRequest request) {
        ApiResponse response;

        try {
            response = new GetNewAddressResponse(apiProxy.getNewAddress(((GetNewAddressRequest) request).getSeed(),
                    ((GetNewAddressRequest) request).getSecurity(),
                    ((GetNewAddressRequest) request).getIndex(),
                    ((GetNewAddressRequest) request).isChecksum(),
                    ((GetNewAddressRequest) request).getTotal(),
                    ((GetNewAddressRequest) request).isReturnAll()));
        } catch (ArgumentException e) {
            response = new NetworkError();
        }
        return response;
    }
}
