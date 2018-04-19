package com.thesis.smile.presentation.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivitySettingsBinding;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.utils.KeyboardUtils;


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

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    KeyboardUtils.hideKeyboard(SettingsActivity.this);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
