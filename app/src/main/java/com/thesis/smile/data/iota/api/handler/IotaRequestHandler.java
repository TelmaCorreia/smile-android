package com.thesis.smile.data.iota.api.handler;

import android.content.Context;

import com.thesis.smile.data.iota.api.requests.ApiRequest;
import com.thesis.smile.data.iota.api.responses.ApiResponse;

import jota.IotaAPI;

public abstract class IotaRequestHandler implements RequestHandler {
    final IotaAPI apiProxy;
    final Context context;

    IotaRequestHandler(IotaAPI iotaApi, Context context) {
        this.apiProxy = iotaApi;
        this.context = context;
    }

    public abstract Class<? extends ApiRequest> getType();

    public abstract ApiResponse handle(ApiRequest request);
}