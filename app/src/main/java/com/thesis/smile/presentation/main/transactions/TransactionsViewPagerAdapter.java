package com.thesis.smile.presentation.main.transactions;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thesis.smile.R;
import com.thesis.smile.presentation.main.transactions.buy.BuyFragment;
import com.thesis.smile.presentation.main.transactions.historical_transactions.HistoricalTransactionsFragment;
import com.thesis.smile.presentation.main.transactions.historical_transactions.HistoricalTransactionsViewModel;
import com.thesis.smile.presentation.main.transactions.sell.SellFragment;
import com.thesis.smile.utils.ResourceProvider;

public class TransactionsViewPagerAdapter extends FragmentPagerAdapter {

    private ResourceProvider resourceProvider;
    private BuyFragment buyFragment;
    private SellFragment sellFragment;
    private HistoricalTransactionsFragment historicalTransactionsFragment;

    public TransactionsViewPagerAdapter(FragmentManager fm, ResourceProvider resourceProvider) {
        super(fm);
        this.resourceProvider = resourceProvider;
        this.buyFragment = BuyFragment.newInstance();
        this.sellFragment = SellFragment.newInstance();
        this.historicalTransactionsFragment = HistoricalTransactionsFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return sellFragment;
            case 1:
                return buyFragment;
            case 2:
                return historicalTransactionsFragment;
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return resourceProvider.getString(R.string.tab_buy);
            case 1:
                return resourceProvider.getString(R.string.tab_sell);
            case 2:
                return resourceProvider.getString(R.string.tab_historical_transactions);
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }
    @Override
    public int getCount() {
        return 3;
    }

    public SellFragment getSellFragment(){
        return sellFragment;
    }

    public BuyFragment getBuyFragment() {
        return buyFragment;
    }

    public HistoricalTransactionsFragment getHistoricalTransactionsFragment() {
        return historicalTransactionsFragment;
    }

   /* @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }*/




}
