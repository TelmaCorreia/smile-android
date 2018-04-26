package com.thesis.smile.presentation.info_price;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityInfoPriceBinding;
import com.thesis.smile.presentation.base.BaseActivity;

public class InfoPriceActivity extends BaseActivity<ActivityInfoPriceBinding, InfoPriceViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, InfoPriceActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int layoutResId() {
        return R.layout.activity_info_price;
    }

    @Override
    protected Class<InfoPriceViewModel> viewModelClass() {
        return InfoPriceViewModel.class;
    }

    @Override
    protected void initViews(ActivityInfoPriceBinding binding) {

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeClose()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    finish();
                });
    }
}
