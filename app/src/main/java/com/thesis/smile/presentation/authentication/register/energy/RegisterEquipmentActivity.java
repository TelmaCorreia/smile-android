package com.thesis.smile.presentation.authentication.register.energy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.databinding.ActivityRegisterEquipmentBinding;
import com.thesis.smile.presentation.authentication.register.energy.info.CycleInfoActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.GeneralInfoActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.main.MainActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.adapters.NothingSelectedSpinnerAdapter;
import com.thesis.smile.presentation.utils.views.CustomDialog;

public class RegisterEquipmentActivity extends BaseActivity<ActivityRegisterEquipmentBinding, RegisterEquipmentViewModel> {

    private static final String REQUEST = "REQUEST";

    private RegisterRequest request;
    private CustomDialog dialogAutomaticConfig;
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


        String[] smartMeters = new String []{"SMP1"};
        ArrayAdapter<CharSequence> adapterSmartMeters = new ArrayAdapter(this,R.layout.layout_spinner_item, smartMeters);
        adapterSmartMeters.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spSmartMeter.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterSmartMeters, R.layout.layout_spinner_item_nothing_selected_smart_meters,this));

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

        getViewModel().observeStartMain()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    MainActivity.launch(this);
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
            dialogAutomaticConfig.setMessage(R.string.automatic_config_description);
            dialogAutomaticConfig.setSecondMessage(R.string.automatic_config_changes);
            dialogAutomaticConfig.setOkButtonText(R.string.button_manual);
            dialogAutomaticConfig.setCloseButtonText(R.string.button_automatic);
            dialogAutomaticConfig.setDismissible(true);
            dialogAutomaticConfig.setOnOkClickListener(() -> {getViewModel().setManual(true); getViewModel().setUserType(getUserType());  getViewModel().setRequest(request); dialogAutomaticConfig.dismiss();});
            dialogAutomaticConfig.setOnCloseClickListener(() ->{getViewModel().setManual(false); getViewModel().setRequest(request); dialogAutomaticConfig.dismiss();});
        }
        if(event instanceof OpenDialogEvent){
            dialogAutomaticConfig.show();
        }else{
            dialogAutomaticConfig.dismiss();
        }
    }
}
