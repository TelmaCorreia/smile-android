package com.thesis.smile.presentation.settings;

import android.content.Context;
import android.content.Intent;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivitySettingsBinding;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;


public class SettingsActivity extends BaseToolbarActivity<ActivitySettingsBinding, SettingsViewModel> {

    RxPermissions rxPermissions;
    SettingsViewPagerAdapter pagerAdapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_settings;
    }

    @Override
    protected Class<SettingsViewModel> viewModelClass() {
        return SettingsViewModel.class;
    }

    @Override
    protected void initViews(ActivitySettingsBinding binding) {
        initToolbar(binding.actionBar.toolbar, true,  getResources().getString(R.string.home_title));

        rxPermissions = new RxPermissions(this);

        pagerAdapter = new SettingsViewPagerAdapter(getSupportFragmentManager(), getResourceProvider());
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewpager);

    }
}
