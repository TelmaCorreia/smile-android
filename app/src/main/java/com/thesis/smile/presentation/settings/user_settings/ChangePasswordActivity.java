package com.thesis.smile.presentation.settings.user_settings;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityChangePasswordBinding;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.settings.SettingsViewModel;

public class ChangePasswordActivity extends BaseToolbarActivity<ActivityChangePasswordBinding, ChangePasswordViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected Class<ChangePasswordViewModel> viewModelClass() {
        return ChangePasswordViewModel.class;
    }

    @Override
    protected void initViews(ActivityChangePasswordBinding binding) {
        setupUI(binding.parent, this);
        initToolbar(binding.actionBar.toolbar, true,  getResources().getString(R.string.recover_pass_title));
    }
}
