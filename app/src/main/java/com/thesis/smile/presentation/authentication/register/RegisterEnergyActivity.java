package com.thesis.smile.presentation.authentication.register;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.databinding.ActivityLoginBinding;
import com.thesis.smile.databinding.ActivityRegisterEnergyBinding;
import com.thesis.smile.databinding.ActivityRegisterUserBinding;
import com.thesis.smile.presentation.authentication.login.LoginViewModel;
import com.thesis.smile.presentation.base.BaseActivity;

public class RegisterEnergyActivity extends BaseActivity<ActivityRegisterEnergyBinding, RegisterEnergyViewModel> {

    private static final String REQUEST = "REQUEST";

    private Bundle request;
    public static void launch(Context context, RegisterRequest registerRequest) {
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

       /* String[] categories = getViewModel().getConfigs().getCategories().values().toArray(new String[0]);
        String[] powers = getViewModel().getConfigs().getPower().values().toArray(new String[0]);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spCategory.setAdapter(adapter);*/


    }

    @Override
    protected void initArguments(Bundle args) {
        super.initArguments(args);
        this.request = args.getBundle(REQUEST);
    }
}
