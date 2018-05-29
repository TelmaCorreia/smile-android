package com.thesis.smile.presentation.settings.user_settings;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentUserSettingsBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.utils.KeyboardUtils;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.views.CustomInputDialog;

public class UserSettingsFragment extends BaseFragment<FragmentUserSettingsBinding, UserSettingsViewModel> {

    private CustomInputDialog showSeedDialog;

    public static UserSettingsFragment newInstance() {
        UserSettingsFragment f = new UserSettingsFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_user_settings;
    }

    @Override
    protected Class<UserSettingsViewModel> viewModelClass() {
        return UserSettingsViewModel.class;
    }

    /*@Override
    protected boolean sharedViewModel(){
        return true;
    }*/

    @Override
    protected void initViews(FragmentUserSettingsBinding binding) {
        setupUI(binding.parent, this.getActivity());

        initRadioButton();
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().openChangePasswordObservable()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    ChangePasswordActivity.launch(getContext());
                });

        getViewModel().observeRadio()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::updateRadio);

        getViewModel().observeShowSeedDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::showSeedDialog);

    }

    private void showSeedDialog(DialogEvent dialogEvent) {
        showSeedDialog = new CustomInputDialog(this.getActivity());
        showSeedDialog.setTitle(R.string.dialog_show_seed_title);
        showSeedDialog.setMessage(R.string.dialog_show_seed_description);
        showSeedDialog.setPrompt(R.string.prompt_pass);
        showSeedDialog.setOkButtonText(R.string.button_ok);
        showSeedDialog.setCloseButtonText(R.string.button_cancel);
        showSeedDialog.setDismissible(true);
        showSeedDialog.setOnOkClickListener(() -> {
            getViewModel().decrypSeed(showSeedDialog.getInput());
            showSeedDialog.dismiss();
        });
        showSeedDialog.setOnCloseClickListener(() ->{showSeedDialog.dismiss();});
        showSeedDialog.show();
    }

    private void initRadioButton() {
        if(getViewModel().getUserType().equals(getString(R.string.consumer))) {
            getBinding().rbConsumer.setChecked(true);
            getBinding().rbProsumer.setChecked(false);
        }else{
            getBinding().rbConsumer.setChecked(false);
            getBinding().rbProsumer.setChecked(true);
        }
    }

    private void updateRadio(Event event) {

        if(getBinding().rbConsumer.isChecked()) {
            getViewModel().setUserType(getString(R.string.consumer));
        }else{
            getViewModel().setUserType(getString(R.string.prosumer));
        }
    }


}
