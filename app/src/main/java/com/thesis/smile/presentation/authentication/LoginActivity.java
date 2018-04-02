package com.thesis.smile.presentation.authentication;
import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityLoginBinding;
import com.thesis.smile.presentation.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    }
}
