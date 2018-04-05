package com.thesis.smile.presentation.authentication.register.energy;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityGeneralInfoBinding;
import com.thesis.smile.presentation.base.BaseActivity;

public class GeneralInfoActivity  extends BaseActivity<ActivityGeneralInfoBinding, GeneralInfoViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, GeneralInfoActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int layoutResId() {
        return R.layout.activity_general_info;
    }

    @Override
    protected Class<GeneralInfoViewModel> viewModelClass() {
        return GeneralInfoViewModel.class;
    }

    @Override
    protected void initViews(ActivityGeneralInfoBinding binding) {

    }
}
