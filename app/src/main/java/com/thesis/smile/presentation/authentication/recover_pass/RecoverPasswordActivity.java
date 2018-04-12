package com.thesis.smile.presentation.authentication.recover_pass;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityRecoverPasswordBinding;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.base.BaseActivity;

public class RecoverPasswordActivity extends BaseActivity<ActivityRecoverPasswordBinding, RecoverPasswordViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, RecoverPasswordActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int layoutResId() {
        return R.layout.activity_recover_password;
    }

    @Override
    protected Class<RecoverPasswordViewModel> viewModelClass() {
        return RecoverPasswordViewModel.class;
    }

    @Override
    protected void initViews(ActivityRecoverPasswordBinding binding) {

    }
}
