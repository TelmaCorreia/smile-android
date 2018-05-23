package com.thesis.smile.data.iota.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.thesis.smile.Constants;
import com.thesis.smile.data.iota.api.handler.GetBundleRequestHandler;
import com.thesis.smile.data.iota.api.handler.GetNewAddressRequestHandler;
import com.thesis.smile.data.iota.api.handler.ReplayBundleRequestHandler;
import com.thesis.smile.data.iota.api.handler.RequestHandler;
import com.thesis.smile.data.iota.api.handler.SendTransferRequestHandler;
import com.thesis.smile.data.iota.api.requests.ApiRequest;
import com.thesis.smile.data.iota.api.responses.ApiResponse;
import com.thesis.smile.data.iota.api.responses.error.NetworkError;
import com.thesis.smile.data.iota.api.responses.error.NetworkErrorType;

import java.util.HashMap;
import java.util.Map;

import cfb.pearldiver.PearlDiverLocalPoW;
import jota.IotaAPI;

public class IotaApiProvider implements ApiProvider {
    private final IotaAPI iotaApi;
    private final Context context;
    private Map<Class<? extends ApiRequest>, RequestHandler> requestHandlerMap;

    public IotaApiProvider(String protocol, String host, int port, Context context) {

        this.iotaApi = new IotaAPI.Builder().protocol(protocol).host(host).port(((Integer) port).toString()).build();

        this.context = context;
        loadRequestMap();
    }

    private void loadRequestMap() {
        Map<Class<? extends ApiRequest>, RequestHandler> requestHandlerMap = new HashMap<>();

        GetBundleRequestHandler getBundleAction = new GetBundleRequestHandler(iotaApi, context);
        GetNewAddressRequestHandler getNewAddressAction = new GetNewAddressRequestHandler(iotaApi, context);
        ReplayBundleRequestHandler replayBundleAction = new ReplayBundleRequestHandler(iotaApi, context);
        SendTransferRequestHandler sendTransferAction = new SendTransferRequestHandler(iotaApi, context);

        requestHandlerMap.put(getBundleAction.getType(), getBundleAction);
        requestHandlerMap.put(getNewAddressAction.getType(), getNewAddressAction);
        requestHandlerMap.put(replayBundleAction.getType(), replayBundleAction);
        requestHandlerMap.put(sendTransferAction.getType(), sendTransferAction);

        this.requestHandlerMap = requestHandlerMap;
    }

    @Override
    public ApiResponse processRequest(ApiRequest apiRequest) {
        ApiResponse response = null;

        try {
            if (this.requestHandlerMap.containsKey(apiRequest.getClass())) {
                RequestHandler requestHandler = this.requestHandlerMap.get(apiRequest.getClass());
                response = requestHandler.handle(apiRequest);
            }
        } catch (IllegalAccessError e) {
            NetworkError error = new NetworkError();
            error.setErrorType(NetworkErrorType.ACCESS_ERROR);
            response = error;
            e.printStackTrace();
        } catch (Exception e) {
            response = new NetworkError();
        }
        return response == null ? new NetworkError() : response;
    }
}

