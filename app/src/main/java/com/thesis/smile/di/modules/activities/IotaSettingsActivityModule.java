package com.thesis.smile.di.modules.activities;

import com.thesis.smile.di.modules.fragments.DayIotaFragmentModule;
import com.thesis.smile.di.modules.fragments.EnergySettingsFragmentModule;
import com.thesis.smile.di.modules.fragments.MonthIotaFragmentModule;
import com.thesis.smile.di.modules.fragments.WeekIotaFragmentModule;
import com.thesis.smile.di.scopes.FragmentScope;
import com.thesis.smile.presentation.iota_settings.IotaSettingsActivity;
import com.thesis.smile.presentation.iota_settings.pager_fragments.DayIotaFragment;
import com.thesis.smile.presentation.iota_settings.pager_fragments.MonthIotaFragment;
import com.thesis.smile.presentation.iota_settings.pager_fragments.WeekIotaFragment;
import com.thesis.smile.presentation.privacy_policy.PrivacyPolicyActivity;
import com.thesis.smile.presentation.settings.energy_settings.EnergySettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class IotaSettingsActivityModule extends BaseActivityModule<IotaSettingsActivity> {

    @FragmentScope
    @ContributesAndroidInjector(modules = DayIotaFragmentModule.class)
    abstract DayIotaFragment dayIotaFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = WeekIotaFragmentModule.class)
    abstract WeekIotaFragment weekIotaFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = MonthIotaFragmentModule.class)
    abstract MonthIotaFragment monthIotaFragment();

}
