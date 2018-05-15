package com.thesis.smile.presentation.authentication.login;
import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityLoginBinding;
import com.thesis.smile.presentation.authentication.recover_pass.RecoverPasswordActivity;
import com.thesis.smile.presentation.authentication.register.RegisterUserActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.main.MainActivity;
import com.thesis.smile.presentation.utils.KeyboardUtils;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected Class viewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected void initViews(ActivityLoginBinding binding) {
        setupUI(binding.parent, this);

    }
    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeOpenRegister()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    RegisterUserActivity.launch(this);
                });

        getViewModel().observeOpenRecoverPassword()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    RecoverPasswordActivity.launch(this);
                });


        getViewModel().observeStartLogin()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    KeyboardUtils.hideKeyboard(this);
                    MainActivity.launch(this);
                });
    }

}
