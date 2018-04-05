package com.thesis.smile.presentation.authentication.register;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.thesis.smile.R;
import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.databinding.ActivityLoginBinding;
import com.thesis.smile.databinding.ActivityRegisterEnergyBinding;
import com.thesis.smile.databinding.ActivityRegisterUserBinding;
import com.thesis.smile.presentation.authentication.login.LoginViewModel;
import com.thesis.smile.presentation.base.BaseActivity;
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
        String[] powers = getViewModel().getConfigs().getPower().values().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterPowers = new ArrayAdapter(this,R.layout.layout_spinner_item, powers);
        adapterPowers.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spPower.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterPowers, R.layout.layout_spinner_item_nothing_selected_power,this));

        String[] tariffs = getViewModel().getConfigs().getTariff().values().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterTariffs = new ArrayAdapter(this,R.layout.layout_spinner_item, tariffs);
        adapterPowers.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spTariff.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterTariffs, R.layout.layout_spinner_item_nothing_selected_tariff,this));

        String[] cycles = getViewModel().getConfigs().getCycle().values().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterCycles = new ArrayAdapter(this,R.layout.layout_spinner_item, cycles);
        adapterPowers.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spCycle.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterCycles, R.layout.layout_spinner_item_nothing_selected_cycle,this));


    }



    @Override
    protected void initArguments(Bundle args) {
        super.initArguments(args);
        Gson gson = new Gson();
        String req = args.getString(REQUEST);
        this.request = gson.fromJson(req, RegisterRequest.class);

    }
}
