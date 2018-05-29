package com.thesis.smile.presentation.authentication.recover_pass;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityRecoverPasswordBinding;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.views.CustomInputDialog;

public class RecoverPasswordActivity extends BaseActivity<ActivityRecoverPasswordBinding, RecoverPasswordViewModel> {

    private CustomInputDialog pinDialog;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RecoverPasswordActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int layoutResId() {
        return R.layout.activity_recover_password;
    }

    @Override
    protected Class<RecoverPasswordViewModel> viewModelClass() {
        return RecoverPasswordViewModel.class;
    }

    @Override
    protected void initViews(ActivityRecoverPasswordBinding binding) {
        setupUI(binding.parent, this);
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();
        getViewModel().observePinDialog()
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::showDialog);

        getViewModel().observeBackToLogin()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    LoginActivity.launch(this);
                });
    }

    private void showDialog(DialogEvent dialogEvent) {
        pinDialog = new CustomInputDialog(this);
        pinDialog.setTitle(R.string.dialog_pin_title);
        pinDialog.setMessage(R.string.dialog_pin_description);
        pinDialog.setPrompt(R.string.prompt_pin);
        pinDialog.setOkButtonText(R.string.button_ok);
        pinDialog.setCloseButtonText(R.string.button_cancel);
        pinDialog.setDismissible(true);
        pinDialog.setOnOkClickListener(() -> {getViewModel().recoverPasswordStep2(pinDialog.getInput()); pinDialog.dismiss();});
        pinDialog.setOnCloseClickListener(() ->{pinDialog.dismiss();});
        pinDialog.show();
    }
}
