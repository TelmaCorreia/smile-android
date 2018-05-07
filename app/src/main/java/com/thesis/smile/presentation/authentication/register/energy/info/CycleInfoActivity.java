package com.thesis.smile.presentation.authentication.register.energy.info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityCycleInfoBinding;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;

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
        binding.tvForm.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeClose()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    finish();
                });

        getViewModel().observeEmail()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::openEmail);

        getViewModel().observeForm()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::openForm);

        getViewModel().observeTel()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::openTel);
    }

    private void openTel(Event event) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(getString(R.string.eem_phone_contact)));
        startActivity(intent);
    }

    private void openForm(Event event) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.eem_form_link)));
        startActivity(browserIntent);
    }

    private void openEmail(Event event) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.eem_email) });
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.eem_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.eem_email_body));
        startActivity(Intent.createChooser(intent, ""));

    }
}
