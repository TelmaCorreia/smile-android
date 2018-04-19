package com.thesis.smile.presentation.main.transactions.timers;

import android.content.Context;
import android.content.Intent;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityTimersBinding;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.settings.SettingsViewPagerAdapter;


public class TimersActivity extends BaseToolbarActivity<ActivityTimersBinding, TimersViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, TimersActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_timers;
    }

    @Override
    protected Class<TimersViewModel> viewModelClass() {
        return TimersViewModel.class;
    }

    @Override
    protected void initViews(ActivityTimersBinding binding) {
        initToolbar(binding.actionBar.toolbar, true,  getResources().getString(R.string.timers_title));


    }
}
