package com.thesis.smile.presentation.main.transactions;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentTransactionsBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;
import com.thesis.smile.presentation.main.historical.HistoricalFragment;
import com.thesis.smile.presentation.main.home.HomeViewModel;
import com.thesis.smile.presentation.main.ranking.RankingFragment;
import com.thesis.smile.presentation.main.transactions.buy.BuyFragment;
import com.thesis.smile.presentation.main.transactions.historical_transactions.HistoricalTransactionsFragment;
import com.thesis.smile.presentation.main.transactions.sell.SellFragment;
import com.thesis.smile.presentation.settings.SettingsViewPagerAdapter;

public class TransactionsFragment extends BaseFragment<FragmentTransactionsBinding, TransactionsViewModel> {

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }


    TransactionsViewPagerAdapter pagerAdapter;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_transactions;
    }

    @Override
    protected Class<TransactionsViewModel> viewModelClass() {
        return TransactionsViewModel.class;
    }

    @Override
    protected void initViews(FragmentTransactionsBinding binding) {
        pagerAdapter = new TransactionsViewPagerAdapter(getChildFragmentManager(), getResourceProvider());
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewpager);

    }
}
