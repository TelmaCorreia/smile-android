package com.thesis.smile.di.modules.activities;

import com.thesis.smile.di.modules.fragments.HistoricalFragmentModule;
import com.thesis.smile.di.modules.fragments.HomeFragmentModule;
import com.thesis.smile.di.modules.fragments.RankingFragmentModule;
import com.thesis.smile.di.modules.fragments.TransactionsFragmentModule;
import com.thesis.smile.di.scopes.FragmentScope;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.main.MainActivity;
import com.thesis.smile.presentation.main.historical.HistoricalFragment;
import com.thesis.smile.presentation.main.home.HomeFragment;
import com.thesis.smile.presentation.main.ranking.RankingFragment;
import com.thesis.smile.presentation.main.transactions.TransactionsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule extends BaseActivityModule<MainActivity> {

    @FragmentScope
    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment homeFragmentInjector();

    @FragmentScope
    @ContributesAndroidInjector(modules = HistoricalFragmentModule.class)
    abstract HistoricalFragment historicalFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = TransactionsFragmentModule.class)
    abstract TransactionsFragment transactionsFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = RankingFragmentModule.class)
    abstract RankingFragment rankingFragment();

}
