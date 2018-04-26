package com.thesis.smile.presentation.privacy_policy;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityPrivacyPolicyBinding;
import com.thesis.smile.presentation.base.BaseActivity;

public class PrivacyPolicyActivity  extends BaseActivity<ActivityPrivacyPolicyBinding, PrivacyPolicyViewModel>{

    public static void launch(Context context) {
        Intent intent = new Intent(context, PrivacyPolicyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    protected Class<PrivacyPolicyViewModel> viewModelClass() {
        return PrivacyPolicyViewModel.class;
    }

    @Override
    protected void initViews(ActivityPrivacyPolicyBinding binding) {

    }
}
