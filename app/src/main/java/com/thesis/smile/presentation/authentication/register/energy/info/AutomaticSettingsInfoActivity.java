package com.thesis.smile.presentation.authentication.register.energy.info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityAutomaticSettingsInfoBinding;
import com.thesis.smile.databinding.ActivityCycleInfoBinding;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.utils.actions.events.Event;

public class AutomaticSettingsInfoActivity extends BaseActivity<ActivityAutomaticSettingsInfoBinding, AutomaticSettingsInfoViewModel> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, AutomaticSettingsInfoActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int layoutResId() {
        return R.layout.activity_automatic_settings_info;
    }

    @Override
    protected Class<AutomaticSettingsInfoViewModel> viewModelClass() {
        return AutomaticSettingsInfoViewModel.class;
    }

    @Override
    protected void initViews(ActivityAutomaticSettingsInfoBinding binding) {
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
