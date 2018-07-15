package com.thesis.smile.domain.managers;


import com.thesis.smile.Constants;
import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.iota.IotaTaskManager;
import com.thesis.smile.iota.requests.GetAccountDataRequest;
import com.thesis.smile.iota.requests.GetNewAddressRequest;
import com.thesis.smile.iota.requests.NodeInfoRequest;
import com.thesis.smile.iota.requests.ReplayBundleRequest;
import com.thesis.smile.iota.requests.SendTransferRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IotaManager {

    public static final String TAG = IotaManager.class.getSimpleName();
    private SharedPrefs sharedPrefs;
    private IotaTaskManager iotaTaskManager;
    List<Address>addresses;

    @Inject
    public IotaManager(IotaTaskManager iotaTaskManager, SharedPrefs sharedPrefs) {
        this.iotaTaskManager = iotaTaskManager;
        this.sharedPrefs = sharedPrefs;
        this.addresses = new ArrayList<>();

    }


    public void generateNewAddress(String seed) {
        GetNewAddressRequest gtr = new GetNewAddressRequest();
        gtr.setSeed(String.valueOf(seed));
        iotaTaskManager.startNewRequestTask(gtr);
    }

    public void attachNewAddress(String seed, String address) {
        //0 value transfer is required to attachToTangle
        SendTransferRequest sendTransferRequest = new SendTransferRequest(seed, address, "0", "", Constants.NEW_ADDRESS_TAG);
        iotaTaskManager.startNewRequestTask(sendTransferRequest);
    }

    public void getAccountData(String seed) {
        try {
            GetAccountDataRequest getAccountDataRequest = new GetAccountDataRequest(seed);
            iotaTaskManager.startNewRequestTask(getAccountDataRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getNodeInfo() {
        NodeInfoRequest nir = new NodeInfoRequest();
        iotaTaskManager.startNewRequestTask(nir);

    }

    public void sendTransfer(String address, String seed, String value ) {
        SendTransferRequest sendTransferRequest = new SendTransferRequest(seed, address, value, "", Constants.NEW_TRANSFER_TAG);
        iotaTaskManager.startNewRequestTask(sendTransferRequest);
    }


    public void reattachAddress(String hash) {
        ReplayBundleRequest replayBundleRequest = new ReplayBundleRequest(hash);
        iotaTaskManager.startNewRequestTask(replayBundleRequest);

    }

    public String getSeed() {
        return sharedPrefs.getSeed();
    }
}
