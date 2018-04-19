package com.thesis.smile.presentation.settings.user_settings;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentUserSettingsBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.utils.KeyboardUtils;

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
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().openChangePasswordObservable()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    ChangePasswordActivity.launch(getContext());
                });

    }

}
