package com.thesis.smile.di.modules.fragments;

import com.thesis.smile.presentation.main.historical.temporal_fragments.DayHistoricalFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.MonthHistoricalFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.WeekHistoricalFragment;
import com.thesis.smile.presentation.main.home.HomeFragment;
import com.thesis.smile.presentation.main.home.pager_fragments.BatteryFragment;
import com.thesis.smile.presentation.main.home.pager_fragments.RenewableFragment;
import com.thesis.smile.presentation.main.home.pager_fragments.TransactionsHomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeFragmentModule extends BaseFragmentModule<HomeFragment> {

    @ContributesAndroidInjector(modules = BatteryFragmentModule.class)
    abstract BatteryFragment batteryFragment();

    @ContributesAndroidInjector(modules = TransactionsHomeFragmentModule.class)
    abstract TransactionsHomeFragment transactionsHomeFragment();

    @ContributesAndroidInjector(modules = RenewableFragmentModule.class)
    abstract RenewableFragment renewableFragment();

}
