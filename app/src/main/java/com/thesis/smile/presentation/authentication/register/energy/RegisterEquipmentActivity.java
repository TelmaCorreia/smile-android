package com.thesis.smile.presentation.authentication.register.energy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.databinding.ActivityRegisterEquipmentBinding;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.iota.responses.GetNewAddressResponse;
import com.thesis.smile.iota.responses.SendTransferResponse;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.presentation.authentication.register.energy.info.AutomaticSettingsInfoActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.main.MainActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.adapters.NothingSelectedSpinnerAdapter;
import com.thesis.smile.presentation.utils.views.CustomDialog;
import com.thesis.smile.presentation.utils.views.SeedPasswordDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

public class RegisterEquipmentActivity extends BaseActivity<ActivityRegisterEquipmentBinding, RegisterEquipmentViewModel> {

    private static final String REQUEST = "REQUEST";

    private RegisterRequest request;
    private CustomDialog dialogAutomaticConfig;
    private SeedPasswordDialog dialoSeedConfig;
    private final String TAG = RegisterEquipmentActivity.class.getCanonicalName();
    private int request_count=0;

    public static void launch(Context context, String registerRequest) {
        Intent intent = new Intent(context, RegisterEquipmentActivity.class);
        intent.putExtra(REQUEST, registerRequest);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_register_equipment;
    }

    @Override
    protected Class viewModelClass() {
        return RegisterEquipmentViewModel.class;
    }

    @Override
    protected void initViews(ActivityRegisterEquipmentBinding binding) {
        setupUI(binding.parent, this);
        String[] batteries = new String []{"3000 W"};
        ArrayAdapter<CharSequence> adapterBatteries = new ArrayAdapter(this,R.layout.layout_spinner_item, batteries);
        adapterBatteries.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spBattery.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterBatteries, R.layout.layout_spinner_item_nothing_selected_battery,this));

        String[] solarPanels = new String []{"500 W"};
        ArrayAdapter<CharSequence> adapterSolarPanels = new ArrayAdapter(this,R.layout.layout_spinner_item, solarPanels);
        adapterSolarPanels.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spSolarPanel.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterSolarPanels, R.layout.layout_spinner_item_nothing_selected_solar_panel,this));


        String[] smartMeters = getViewModel().getConfigs().getSmartMeterIds().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterSmartMeters = new ArrayAdapter(this,R.layout.layout_spinner_item, smartMeters);
        adapterSmartMeters.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spSmartMeter.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterSmartMeters, R.layout.layout_spinner_item_nothing_selected_smart_meters,this));
        binding.spSmartMeter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setSmartMeterId(smartMeters[i-1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void initArguments(Bundle args) {
        super.initArguments(args);
        Gson gson = new Gson();
        String req = args.getString(REQUEST);
        this.request = gson.fromJson(req, RegisterRequest.class);

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

      /*  getViewModel().observeRegister()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                   getViewModel().register(request, getUserType());
                });*/

        getViewModel().observeRadio()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::updateRadio);

        getViewModel().observeAutomaticConfigDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::shareDataDialogEvent);

        getViewModel().observeSeedDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::seedDialogEvent);

        getViewModel().observeStartMain()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    MainActivity.launch(this);
                    finish();
                });

        getViewModel().observeStartTransactions()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    MainActivity.launchTransactions(this);
                    finish();
                });
    }

    private void updateRadio(Event event) {

        if(getBinding().rbConsumer.isChecked()) {
            getViewModel().setUserType(getString(R.string.consumer));
        }else{
            getViewModel().setUserType(getString(R.string.prosumer));
        }
    }

    public String getUserType(){
        int id = getBinding().userTopology.getCheckedRadioButtonId();
        if(id == R.id.rbConsumer){
            return getResources().getString(R.string.consumer);
        }
        return getResources().getString(R.string.prosumer);
    }

    private void shareDataDialogEvent(DialogEvent event){
        if(dialogAutomaticConfig == null){
            dialogAutomaticConfig = new CustomDialog(RegisterEquipmentActivity.this);
            dialogAutomaticConfig.setTitle(R.string.automatic_config_tilte);
            dialogAutomaticConfig.setMessage(getString(R.string.automatic_config_description));
            dialogAutomaticConfig.setSecondMessage(R.string.automatic_config_changes);
            dialogAutomaticConfig.setSpanMessage(R.string.automatic_config_description, R.string.automatic_settings, R.color.colorUnderline);
            dialogAutomaticConfig.setOkButtonText(R.string.button_manual);
            dialogAutomaticConfig.setCloseButtonText(R.string.button_automatic);
            dialogAutomaticConfig.setDismissible(true);
            dialogAutomaticConfig.setOnLinkClickListener(() -> { AutomaticSettingsInfoActivity.launch(this);});
            dialogAutomaticConfig.setOnOkClickListener(() -> {getViewModel().setManual(true); getViewModel().setUserType(getUserType());  getViewModel().setRequest(request); dialogAutomaticConfig.dismiss();});
            dialogAutomaticConfig.setOnCloseClickListener(() ->{getViewModel().setManual(false); getViewModel().setRequest(request); dialogAutomaticConfig.dismiss();});
        }
        if(event instanceof OpenDialogEvent){
            dialogAutomaticConfig.show();
        }else{
            dialogAutomaticConfig.dismiss();
        }
    }

    private void seedDialogEvent(DialogEvent event){
        if(dialoSeedConfig == null){
            String seed = getViewModel().generateSeed();
            dialoSeedConfig = new SeedPasswordDialog(RegisterEquipmentActivity.this);
            dialoSeedConfig.setTitle(R.string.dialog_seed_title);
            dialoSeedConfig.setMessage(getString(R.string.dialog_seed_info) + "\nChave:\n"+seed);
            dialoSeedConfig.setOkButtonText(R.string.button_save);
            dialoSeedConfig.setDismissible(false);
            dialoSeedConfig.setOnOkClickListener(() -> {
                if (!dialoSeedConfig.getInput().isEmpty() && !(dialoSeedConfig.getInput().length()<5)){
                    getViewModel().encryptSeed(dialoSeedConfig.getInput()); dialoSeedConfig.dismiss();
                }else{
                    getViewModel().alertInvalidPassword();
                }
            });

        }
        if(event instanceof OpenDialogEvent){
            dialoSeedConfig.show();
        }else{
            dialoSeedConfig.dismiss();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(GetNewAddressResponse getNewAddressResponse) {
        //attach new
        getViewModel().saveAddress(getNewAddressResponse.getAddresses().get(0));
        getViewModel().attachNewAddress(getNewAddressResponse.getAddresses().get(0));
    }

    @Subscribe
    public void onEvent(SendTransferResponse str) {
        if (Arrays.asList(str.getSuccessfully()).contains(true))
            getViewModel().sendAddress();
    }



    @Subscribe
    public void onEvent(NetworkError error) {
        switch (error.getErrorType()) {
            case ACCESS_ERROR:
                Log.d(TAG, "Access error!");
                break;
            case REMOTE_NODE_ERROR:
                Log.d(TAG, "Remote node error!");
                if (request_count<3){
                    request_count++;
                    getViewModel().generateNewAddress();
                }else{
                    getViewModel().next();
                }
                break;
        }
    }
}
