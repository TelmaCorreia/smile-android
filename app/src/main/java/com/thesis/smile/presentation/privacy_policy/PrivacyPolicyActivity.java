package com.thesis.smile.presentation.privacy_policy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityPrivacyPolicyBinding;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.utils.actions.events.Event;

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
        String email= getString(R.string.project_email);
        String iota_link= getString(R.string.privacy_policy_subtitle1_1_text_1);
        String fabric_link= getString(R.string.privacy_policy_subtitle1_1_text_3);

        SpannableString ss=  new SpannableString(iota_link);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorUnderline)), iota_link.indexOf(getString(R.string.iota_link)), iota_link.indexOf(getString(R.string.iota_link))+getString(R.string.iota_link).length(), 0);
        binding.link.setText(ss);

        ss=  new SpannableString(fabric_link);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorUnderline)), fabric_link.indexOf(getString(R.string.fabric_link)), fabric_link.indexOf(getString(R.string.fabric_link))+getString(R.string.fabric_link).length(), 0);
        binding.link1.setText(ss);

        ss=  new SpannableString(email);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorUnderline)), email.indexOf(getString(R.string.project_email)), email.indexOf(getString(R.string.project_email))+getString(R.string.project_email).length(), 0);
        binding.email.setText(ss);
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeClose()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    finish();
                });

        getViewModel().observeLink()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::openLink);

        getViewModel().observeLink1()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::openLink1);

        getViewModel().observeEmail()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::openEmail);
    }

    private void openLink(Event event) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.iota_link)));
        startActivity(browserIntent);
    }

    private void openLink1(Event event) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.fabric_link)));
        startActivity(browserIntent);
    }

    private void openEmail(Event event) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.project_email) });
        startActivity(Intent.createChooser(intent, ""));

    }
}
