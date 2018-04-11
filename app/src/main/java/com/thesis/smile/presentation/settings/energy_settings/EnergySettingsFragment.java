package com.thesis.smile.presentation.settings.energy_settings;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentEnergySettingsBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class EnergySettingsFragment  extends BaseFragment<FragmentEnergySettingsBinding, EnergySettingsViewModel> {

    public static EnergySettingsFragment newInstance() {
        EnergySettingsFragment f = new EnergySettingsFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_energy_settings;
    }

    @Override
    protected Class<EnergySettingsViewModel> viewModelClass() {
        return EnergySettingsViewModel.class;
    }

    @Override
    protected void initViews(FragmentEnergySettingsBinding binding) {

    }
}
