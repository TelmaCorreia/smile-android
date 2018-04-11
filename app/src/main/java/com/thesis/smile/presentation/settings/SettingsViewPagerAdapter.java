package com.thesis.smile.presentation.settings;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thesis.smile.R;
import com.thesis.smile.presentation.settings.energy_settings.EnergySettingsFragment;
import com.thesis.smile.presentation.settings.user_settings.UserSettingsFragment;
import com.thesis.smile.utils.ResourceProvider;

public class SettingsViewPagerAdapter extends FragmentPagerAdapter {

    private ResourceProvider resourceProvider;
    private EnergySettingsFragment energySettingsFragment;
    private UserSettingsFragment userSettingsFragment;

    public SettingsViewPagerAdapter(FragmentManager fm, ResourceProvider resourceProvider) {
        super(fm);
        this.resourceProvider = resourceProvider;
        this.energySettingsFragment = EnergySettingsFragment.newInstance();
        this.userSettingsFragment = UserSettingsFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return energySettingsFragment;
            case 1:
                return userSettingsFragment;
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return resourceProvider.getString(R.string.tab_user_settings);
            case 1:
                return resourceProvider.getString(R.string.tab_energy_settings);
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }
    @Override
    public int getCount() {
        return 2;
    }

    public UserSettingsFragment getUserSettingsFragment(){
        return userSettingsFragment;
    }

    public EnergySettingsFragment getEnergySettingsFragment() {
        return energySettingsFragment;
    }




}
