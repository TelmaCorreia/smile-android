package com.thesis.smile.iota.handler;

import android.content.Context;

import com.thesis.smile.iota.requests.ApiRequest;
import com.thesis.smile.iota.requests.NodeInfoRequest;
import com.thesis.smile.iota.responses.ApiResponse;
import com.thesis.smile.iota.responses.NodeInfoResponse;

import jota.IotaAPI;

public class NodeInfoRequestHandler  extends IotaRequestHandler {
    public NodeInfoRequestHandler(IotaAPI apiProxy, Context context) {
        super(apiProxy, context);
    }

    @Override
    public Class<? extends ApiRequest> getType() {
        return NodeInfoRequest.class;
    }

    @Override
    public ApiResponse handle(ApiRequest request) {
        return new NodeInfoResponse(this.apiProxy.getNodeInfo());
    }
}

