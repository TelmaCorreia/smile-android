package com.thesis.smile.domain.managers;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.iota.IotaTaskManager;
import com.thesis.smile.iota.requests.GetAccountDataRequest;
import com.thesis.smile.iota.requests.GetNewAddressRequest;
import com.thesis.smile.iota.requests.NodeInfoRequest;
import com.thesis.smile.iota.requests.SendTransferRequest;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.iota.responses.GetNewAddressResponse;
import com.thesis.smile.iota.responses.NodeInfoResponse;
import com.thesis.smile.iota.responses.SendTransferResponse;
import com.thesis.smile.iota.responses.error.NetworkError;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IotaManager {

    public static final String TAG = IotaManager.class.getSimpleName();
    private SharedPrefs sharedPrefs;
    private IotaTaskManager iotaTaskManager;
    private final String seed;
    List<Address>addresses;

    @Inject
    public IotaManager(IotaTaskManager iotaTaskManager, SharedPrefs sharedPrefs) {
        this.iotaTaskManager = iotaTaskManager;
        this.sharedPrefs = sharedPrefs;
        this.addresses = new ArrayList<>();

        seed = sharedPrefs.getSeed();
    }


    public void generateNewAddress() {
        GetNewAddressRequest gtr = new GetNewAddressRequest();
        gtr.setSeed(String.valueOf(seed));
        iotaTaskManager.startNewRequestTask(gtr);
    }

    public void attachNewAddress(String address) {
        //0 value transfer is required to attachToTangle
        SendTransferRequest sendTransferRequest = new SendTransferRequest(seed, address, "0", "", Constants.NEW_ADDRESS_TAG);
        iotaTaskManager.startNewRequestTask(sendTransferRequest);
    }

    public void getAccountData() {
        GetAccountDataRequest getAccountDataRequest = new GetAccountDataRequest(seed);
        iotaTaskManager.startNewRequestTask(getAccountDataRequest);
    }

    public void getNodeInfo() {
        NodeInfoRequest nir = new NodeInfoRequest();
        iotaTaskManager.startNewRequestTask(nir);

    }
    /*@Subscribe
    public void onEvent(GetAccountDataResponse gad) {
        addresses = gad.getAddresses();
    }

    @Subscribe
    public void onEvent(GetNewAddressResponse getNewAddressResponse) {
        //attach new
        attachNewAddress(getNewAddressResponse.getAddresses().get(0));
    }

    @Subscribe
    public void onEvent(SendTransferResponse str) {
        if (Arrays.asList(str.getSuccessfully()).contains(true))
            getAccountData();
    }

    @Subscribe
    public void onEvent(NodeInfoResponse nodeInfoResponse) {
        if (nodeInfoResponse.getLatestMilestoneIndex() == (nodeInfoResponse.getLatestSolidSubtangleMilestoneIndex())) {
            getAccountData();
        } else {
            Log.d(TAG, "NOT synced!");
        }
    }


    @Subscribe
    public void onEvent(NetworkError error) {
        switch (error.getErrorType()) {
            case ACCESS_ERROR:
                Log.d(TAG, "Access errot!");
                getNodeInfo();
                break;
            case REMOTE_NODE_ERROR:
                Log.d(TAG, "Remote node errot!");
                addresses.clear();
                break;
        }
    }
*/

}
