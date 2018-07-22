package com.thesis.smile.presentation.settings.user_settings;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityShareDataInfoBinding;
import com.thesis.smile.presentation.base.BaseActivity;


public class ShareDataInfoActivity extends BaseActivity<ActivityShareDataInfoBinding, ShareDataInfoViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, ShareDataInfoActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int layoutResId() {
        return R.layout.activity_share_data_info;
    }

    @Override
    protected Class<ShareDataInfoViewModel> viewModelClass() {
        return ShareDataInfoViewModel.class;
    }

    @Override
    protected void initViews(ActivityShareDataInfoBinding binding) {
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
