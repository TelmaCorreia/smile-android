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
import com.thesis.smile.databinding.ActivityRegisterEnergyBinding;

import com.thesis.smile.presentation.authentication.register.energy.info.CycleInfoActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.GeneralInfoActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.utils.SortUtils;
import com.thesis.smile.presentation.utils.adapters.NothingSelectedSpinnerAdapter;

public class RegisterEnergyActivity extends BaseActivity<ActivityRegisterEnergyBinding, RegisterEnergyViewModel> {

    private static final String REQUEST = "REQUEST";

    private RegisterRequest request;
    public static void launch(Context context, String registerRequest) {
        Intent intent = new Intent(context, RegisterEnergyActivity.class);
        intent.putExtra(REQUEST, registerRequest);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_register_energy;
    }

    @Override
    protected Class viewModelClass() {
        return RegisterEnergyViewModel.class;
    }

    @Override
    protected void initViews(ActivityRegisterEnergyBinding binding) {

        String[] categories = getViewModel().getConfigs().getCategories().values().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterCategories = new ArrayAdapter(this,R.layout.layout_spinner_item, categories);
        adapterCategories.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spCategory.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterCategories, R.layout.layout_spinner_item_nothing_selected_category,this));

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setCategory(categories[i-1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] powers = SortUtils.sortMapByKey(getViewModel().getConfigs().getPower()).toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterPowers = new ArrayAdapter(this,R.layout.layout_spinner_item, powers);
        adapterPowers.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spPower.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterPowers, R.layout.layout_spinner_item_nothing_selected_power,this));

        binding.spPower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setPower(powers[i-1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] tariffs = SortUtils.sortMapByKey(getViewModel().getConfigs().getTariff()).toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterTariffs = new ArrayAdapter(this,R.layout.layout_spinner_item, tariffs);
        adapterTariffs.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spTariff.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterTariffs, R.layout.layout_spinner_item_nothing_selected_tariff,this));

        binding.spTariff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setTariff(tariffs[i-1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] cycles = getViewModel().getConfigs().getCycle().values().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterCycles = new ArrayAdapter(this,R.layout.layout_spinner_item, cycles);
        adapterCycles.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spCycle.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterCycles, R.layout.layout_spinner_item_nothing_selected_cycle,this));

        binding.spCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setCycle(cycles[i-1]);
                }else {
                    getViewModel().setCycle(getResources().getString(R.string.no_cycle));
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

        getViewModel().observeNext()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    RegisterEquipmentActivity.launch(this, getViewModel().getRegisterRequest(request));
                });


    }
}
