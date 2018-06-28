package com.thesis.smile.di.modules.fragments;

import com.thesis.smile.presentation.main.historical.HistoricalFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.DayHistoricalFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.MonthHistoricalFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.WeekHistoricalFragment;
import com.thesis.smile.presentation.main.transactions.buy.BuyFragment;
import com.thesis.smile.presentation.main.transactions.historical_transactions.HistoricalTransactionsFragment;
import com.thesis.smile.presentation.main.transactions.sell.SellFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoricalFragmentModule extends BaseFragmentModule<HistoricalFragment> {

    @ContributesAndroidInjector(modules = DayHistoricalFragmentModule.class)
    abstract DayHistoricalFragment dayHistoricalFragment();

    @ContributesAndroidInjector(modules = WeekHistoricalFragmentModule.class)
    abstract WeekHistoricalFragment weekHistoricalFragment();

    @ContributesAndroidInjector(modules = MonthHistoricalFragmentModule.class)
    abstract MonthHistoricalFragment monthHistoricalFragment();

}
