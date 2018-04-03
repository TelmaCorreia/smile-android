package com.thesis.smile.presentation.authentication.register;
import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityLoginBinding;
import com.thesis.smile.databinding.ActivityRegisterEnergyBinding;
import com.thesis.smile.databinding.ActivityRegisterUserBinding;
import com.thesis.smile.presentation.authentication.login.LoginViewModel;
import com.thesis.smile.presentation.base.BaseActivity;

public class RegisterEnergyActivity extends BaseActivity<ActivityRegisterEnergyBinding, RegisterEnergyViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterEnergyActivity.class);
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

    }
}
