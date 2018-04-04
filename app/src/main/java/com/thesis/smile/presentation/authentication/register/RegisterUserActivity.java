package com.thesis.smile.presentation.authentication.register;
import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.databinding.ActivityLoginBinding;
import com.thesis.smile.databinding.ActivityRegisterUserBinding;
import com.thesis.smile.presentation.authentication.login.LoginViewModel;
import com.thesis.smile.presentation.base.BaseActivity;

public class RegisterUserActivity extends BaseActivity<ActivityRegisterUserBinding, RegisterUserViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterUserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_register_user;
    }

    @Override
    protected Class viewModelClass() {
        return RegisterUserViewModel.class;
    }

    @Override
    protected void initViews(ActivityRegisterUserBinding binding) {

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeNext()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    RegisterEnergyActivity.launch(this, getViewModel().getRegisterRequest());
                    finish();
                });

    }
}
