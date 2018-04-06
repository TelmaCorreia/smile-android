package com.thesis.smile.presentation.authentication.register.energy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.databinding.ActivityRegisterEquipmentBinding;
import com.thesis.smile.presentation.authentication.register.energy.info.CycleInfoActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.GeneralInfoActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.adapters.NothingSelectedSpinnerAdapter;
import com.thesis.smile.presentation.utils.views.CustomDialog;

public class RegisterEquipmentActivity extends BaseActivity<ActivityRegisterEquipmentBinding, RegisterEquipmentViewModel> {

    private static final String REQUEST = "REQUEST";

    private RegisterRequest request;
    private CustomDialog dialogShareData;
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


        String[] smartMeters = new String []{"something"};
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

        getViewModel().observeOpenCycleInfo()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    CycleInfoActivity.launch(this);
                });

        getViewModel().observeOpenGeneralInfo()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    GeneralInfoActivity.launch(this);
                });

      /*  getViewModel().observeRegister()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                   getViewModel().register(request, getUserType());
                });*/

        getViewModel().observeShareDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::shareDataDialogEvent);

        //TODO: discomment
        /*getViewModel().observeStartMain()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    MainActivity.launch(this);
                    finish();
                });*/

    }

    public String getUserType(){
        int id = getBinding().userTopology.getCheckedRadioButtonId();
        if(id == R.id.rbConsumer){
            return getResources().getString(R.string.consumer);
        }
        return getResources().getString(R.string.prosumer);
    }

    private void shareDataDialogEvent(DialogEvent event){
        if(dialogShareData == null){
            dialogShareData = new CustomDialog(RegisterEquipmentActivity.this);
            dialogShareData.setTitle(R.string.share_data_tilte);
            dialogShareData.setMessage(R.string.share_data_description);
            dialogShareData.setOkButtonText(R.string.button_allow);
            dialogShareData.setCloseButtonText(R.string.button_not_allow);
            dialogShareData.setDismissible(true);
            dialogShareData.setOnOkClickListener(() -> {getViewModel().setShare(true); getViewModel().setUserType(getUserType());  getViewModel().register(request); dialogShareData.dismiss();});
            dialogShareData.setOnCloseClickListener(() ->{getViewModel().setShare(false); getViewModel().register(request); dialogShareData.dismiss();});
        }
        if(event instanceof OpenDialogEvent){
            dialogShareData.show();
        }else{
            dialogShareData.dismiss();
        }
    }
}
