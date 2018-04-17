package com.thesis.smile.di.modules.fragments;

import com.thesis.smile.presentation.main.transactions.TransactionsFragment;
import com.thesis.smile.presentation.main.transactions.buy.BuyFragment;
import com.thesis.smile.presentation.main.transactions.historical_transactions.HistoricalTransactionsFragment;
import com.thesis.smile.presentation.main.transactions.sell.SellFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TransactionsFragmentModule extends BaseFragmentModule<TransactionsFragment> {

    @ContributesAndroidInjector(modules = BuyFragmentModule.class)
    abstract BuyFragment buyFragment();

    @ContributesAndroidInjector(modules = SellFragmentModule.class)
    abstract SellFragment sellFragment();

    @ContributesAndroidInjector(modules = HistoricalTransactionsFragmentModule.class)
    abstract HistoricalTransactionsFragment historicalTransactionsFragment();


}
