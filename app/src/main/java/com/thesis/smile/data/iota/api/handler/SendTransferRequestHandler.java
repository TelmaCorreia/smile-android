package com.thesis.smile.data.iota.api.handler;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.thesis.smile.Constants;
import com.thesis.smile.data.iota.api.requests.ApiRequest;
import com.thesis.smile.data.iota.api.requests.SendTransferRequest;
import com.thesis.smile.data.iota.api.responses.ApiResponse;
import com.thesis.smile.data.iota.api.responses.SendTransferResponse;
import com.thesis.smile.data.iota.api.responses.error.NetworkError;
import com.thesis.smile.data.iota.api.responses.error.NetworkErrorType;
import com.thesis.smile.utils.iota.Utils;

import java.util.Arrays;

import jota.IotaAPI;
import jota.error.ArgumentException;

public class SendTransferRequestHandler extends IotaRequestHandler {
    public SendTransferRequestHandler(IotaAPI apiProxy, Context context) {
        super(apiProxy, context);
    }

    @Override
    public Class<? extends ApiRequest> getType() {
        return SendTransferRequest.class;
    }

    @Override
    public ApiResponse handle(ApiRequest request) {
        int notificationId = Utils.createNewID();
        ApiResponse response;
        // if we generate a new address the tag == address
        if (((SendTransferRequest) request).getValue().equals("0")
                && ((SendTransferRequest) request).getTag().equals(Constants.NEW_ADDRESS_TAG)) {
                Log.d(SendTransferRequestHandler.class.getCanonicalName(), "Attaching new address" );
            } else {
                Log.d(SendTransferRequestHandler.class.getCanonicalName(), "Sending transfer" );
           }

        try {
            response = new SendTransferResponse(apiProxy.sendTransfer(((SendTransferRequest) request).getSeed(),
                    ((SendTransferRequest) request).getSecurity(),
                    ((SendTransferRequest) request).getDepth(),
                    ((SendTransferRequest) request).getMinWeightMagnitude(),
                    ((SendTransferRequest) request).prepareTransfer(),
                    //inputs
                    null,
                    //remainder address
                    null,
                    false));
        } catch (ArgumentException | IllegalAccessError e) {
            NetworkError error = new NetworkError();

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (mNotificationManager != null) {
                mNotificationManager.cancel(notificationId);
            }

            if (e instanceof ArgumentException) {
                if (e.getMessage().contains("Sending to a used address.") || e.getMessage().contains("Private key reuse detect!") || e.getMessage().contains("Send to inputs!")) {
                    final Activity activity = (Activity) context;
                    Bundle bundle = new Bundle();
                    bundle.putString("error", e.getMessage());
                    error.setErrorType(NetworkErrorType.KEY_REUSE_ERROR);
                }
            }

            if (e instanceof IllegalAccessError) {
                error.setErrorType(NetworkErrorType.ACCESS_ERROR);
                if (((SendTransferRequest) request).getTag().equals(Constants.NEW_ADDRESS_TAG)) {
                    Log.e(SendTransferRequestHandler.class.getCanonicalName(), "Attaching new address failed, please enable local PoW" );
                }else{
                    Log.e(SendTransferRequestHandler.class.getCanonicalName(), "Transaction failed, please enable local PoW" );
                }
            } else {
                if (error.getErrorType() != NetworkErrorType.KEY_REUSE_ERROR) {
                    error.setErrorType(NetworkErrorType.NETWORK_ERROR);
                }
                if (((SendTransferRequest) request).getValue().equals("0") && ((SendTransferRequest) request).getTag().equals(Constants.NEW_ADDRESS_TAG)) {
                    Log.e(SendTransferRequestHandler.class.getCanonicalName(), "Attaching new address failed" );
                } else {
                    Log.e(SendTransferRequestHandler.class.getCanonicalName(), "Transaction failed" );
                }
            }

            response = error;
        }

        if (response instanceof SendTransferResponse && ((SendTransferRequest) request).getValue().equals("0")
                && ((SendTransferRequest) request).getTag().equals(Constants.NEW_ADDRESS_TAG)) {
            if (Arrays.asList(((SendTransferResponse) response).getSuccessfully()).contains(true)) {
                Log.d(SendTransferRequestHandler.class.getCanonicalName(), "Attaching new address executed" );
            }else {
                Log.e(SendTransferRequestHandler.class.getCanonicalName(), "Attaching new address failed" );
            }
        } else if (response instanceof SendTransferResponse) {
            if (Arrays.asList(((SendTransferResponse) response).getSuccessfully()).contains(true)){
                Log.d(SendTransferRequestHandler.class.getCanonicalName(), "Transaction executed" );
            }else{
                Log.e(SendTransferRequestHandler.class.getCanonicalName(), "Transaction failed" );
            }
        }

        return response;
    }
}

