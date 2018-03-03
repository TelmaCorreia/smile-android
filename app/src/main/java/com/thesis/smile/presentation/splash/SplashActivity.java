package com.thesis.smile.presentation.splash;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivitySplashBinding;
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
}
