package com.thesis.smile.presentation.settings.user_settings;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentUserSettingsBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.utils.KeyboardUtils;
import com.thesis.smile.presentation.utils.actions.events.Event;

public class UserSettingsFragment extends BaseFragment<FragmentUserSettingsBinding, UserSettingsViewModel> {

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
