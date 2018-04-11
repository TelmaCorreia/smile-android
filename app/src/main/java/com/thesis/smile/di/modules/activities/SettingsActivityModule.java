package com.thesis.smile.di.modules.activities;

import com.thesis.smile.di.modules.fragments.EnergySettingsFragmentModule;
import com.thesis.smile.di.modules.fragments.HomeFragmentModule;
import com.thesis.smile.di.modules.fragments.UserSettingsFragmentModule;
import com.thesis.smile.di.scopes.FragmentScope;
import com.thesis.smile.presentation.main.home.HomeFragment;
import com.thesis.smile.presentation.settings.SettingsActivity;
import com.thesis.smile.presentation.settings.energy_settings.EnergySettingsFragment;
import com.thesis.smile.presentation.settings.user_settings.UserSettingsFragment;
import com.thesis.smile.presentation.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsActivityModule extends BaseActivityModule<SettingsActivity> {

    @FragmentScope
    @ContributesAndroidInjector(modules = UserSettingsFragmentModule.class)
    abstract UserSettingsFragment userSettingsFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = EnergySettingsFragmentModule.class)
    abstract EnergySettingsFragment energySettingsFragment();
}
