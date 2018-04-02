package com.thesis.smile.presentation.authentication;

import android.databinding.ViewDataBinding;

import com.thesis.smile.R;
import com.thesis.smile.presentation.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    @Override
    protected int layoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected Class viewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected void initViews(ViewDataBinding binding) {

    }
}
