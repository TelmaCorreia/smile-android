package com.thesis.smile.presentation.splash;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivitySplashBinding;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.authentication.register.RegisterUserActivity;
import com.thesis.smile.presentation.base.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    @Override
    protected int layoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected Class<SplashViewModel> viewModelClass() {
        return SplashViewModel.class;
    }

    @Override
    protected void initViews(ActivitySplashBinding binding) {}

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel()
                .observeOpenLogin()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    LoginActivity.launch(this);
                    finish();
                });

       getViewModel()
                .observeOpenMain()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    RegisterUserActivity.launch(this); //FIXME: mainacivity
                    finish();
                });

    }
}
