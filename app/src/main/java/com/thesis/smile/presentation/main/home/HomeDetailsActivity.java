package com.thesis.smile.presentation.main.home;
import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityHomeDetailsBinding;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;

public class HomeDetailsActivity extends BaseToolbarActivity<ActivityHomeDetailsBinding, HomeDetailsViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_home_details;
    }

    @Override
    protected Class viewModelClass() {
        return HomeDetailsViewModel.class;
    }

    @Override
    protected void initViews(ActivityHomeDetailsBinding binding) {
        initToolbar(binding.actionBar.toolbar, true);

    }
    @Override
    protected void registerObservables() {
        super.registerObservables();


    }

}
