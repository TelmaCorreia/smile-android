package com.thesis.smile.presentation.authentication.register.energy;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityCycleInfoBinding;
import com.thesis.smile.presentation.authentication.register.RegisterEnergyActivity;
import com.thesis.smile.presentation.base.BaseActivity;

public class CycleInfoActivity extends BaseActivity<ActivityCycleInfoBinding, CycleInfoViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, CycleInfoActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int layoutResId() {
        return R.layout.activity_cycle_info;
    }

    @Override
    protected Class<CycleInfoViewModel> viewModelClass() {
        return CycleInfoViewModel.class;
    }

    @Override
    protected void initViews(ActivityCycleInfoBinding binding) {

    }
}
