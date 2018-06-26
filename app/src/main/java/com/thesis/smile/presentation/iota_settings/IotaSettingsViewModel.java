package com.thesis.smile.presentation.iota_settings;

import android.databinding.Bindable;
import android.util.DisplayMetrics;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.IotaManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.domain.models_iota.Transfer;
import com.thesis.smile.iota.requests.ReplayBundleRequest;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.iota.AESCrypt;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class IotaSettingsViewModel extends BaseToolbarViewModel {

    List<Address> addresses;
    private String address;
    private IotaManager iotaManager;
    private UserManager userManager;
    private User user;
    private PublishRelay<DialogEvent> showSeedDialog = PublishRelay.create();
    private PublishRelay<DialogEvent> insertPassSeedDialog = PublishRelay.create();
    private boolean confirmed;
    private String seed = "";
    private boolean seedVisible = false;


    @Inject
    public IotaSettingsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager, IotaManager iotaManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.iotaManager = iotaManager;
        this.addresses = new ArrayList<>();
        this.userManager = userManager;
        this.address = userManager.getAddress();

    }

    @Bindable
    public String getSeed() {
        return this.seed;
    }

    public void setSeed(String seed) {
        this.seedVisible=true;
        this.seed=seed;
        userManager.saveSeed(seed); // FIXME: just for test
        notifyPropertiesChanged(BR.seed, BR.seedVisible, BR.hideSeedVisible);

    }

    @Bindable
    public int getSeedVisible() {
        return !seedVisible? View.VISIBLE:View.GONE;
    }

    @Bindable
    public int getHideSeedVisible() {
        return seedVisible? View.VISIBLE:View.GONE;
    }
    public void generateNewAddress() {
        iotaManager.generateNewAddress();
    }

    public void attachNewAddress(String s) {
        iotaManager.attachNewAddress(s);
    }

    public void getAccountData() {
        iotaManager.getAccountData();
    }

    public void onGetIotaClick(){
        insertPassSeedDialog.accept(new DialogEvent());
    }

    public void message(String  msg){
        getUiEvents().showToast(msg);
    }

    public void sendAddress(String address){
        getUiEvents().showToast(userManager.getSeed());
        userManager.saveAddress(address);
        userManager.updateIotaAddress()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onAddressUpdated, this::onError);
    }

    private void onAddressUpdated(User user) {
        message("address updated!!!");
    }

    public void saveAddress(String s) {
        userManager.saveAddress(s);
    }

    public void onShowSeedClick(){
        showSeedDialog.accept(new DialogEvent());
    }

    public void onHideSeedClick(){
        showSeedDialog.accept(new DialogEvent());
    }


    public void decrypSeed(String input) {
        String seed = "";
        this.user = userManager.getCurrentUser();
        if (user!=null && user.getEncryptedSeed()!=null){
            try {
                AESCrypt aes = new AESCrypt(input);
                seed = aes.decrypt(user.getEncryptedSeed());
            } catch (Exception e) {
                getUiEvents().showToast(getResourceProvider().getString(R.string.err_seed_decypher));
            }
            userManager.saveSeed(seed);
        }
    }

    Observable<DialogEvent> observeShowSeedDialog(){
        return showSeedDialog;
    }

    Observable<DialogEvent> observeInsertPassSeedDialog(){
        return insertPassSeedDialog;
    }


    public void getFreeIotas() {
        if(userManager.getAddress()!=null){
            List<Transfer> transfers = getTransfers();
            if(transfers!=null && transfers.size()>0 && !transfers.get(0).getPersistence()){
                iotaManager.reattachAddress(transfers.get(0).getHash());
            }else{
                sendAddress(userManager.getAddress());
            }
        }else{
            generateNewAddress();
        }
    }


    public void saveTransfer(List<Transfer> transfers) {
        userManager.saveTransfers(transfers);
    }



    public List<Transfer> getTransfers() {
        return userManager.getTransfers();
    }


    public void showSeed(String input) {
        decrypSeed(input);
        setSeed(userManager.getSeed());
    }
}
