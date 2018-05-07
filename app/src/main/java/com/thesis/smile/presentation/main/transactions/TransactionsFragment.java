package com.thesis.smile.presentation.main.transactions;

import android.content.Intent;
import android.support.v4.view.ViewPager;

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
import com.thesis.smile.presentation.settings.SettingsActivity;
import com.thesis.smile.presentation.settings.SettingsViewPagerAdapter;
import com.thesis.smile.presentation.utils.KeyboardUtils;

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
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                KeyboardUtils.hideKeyboard(TransactionsFragment.this);
                //binding.viewpager.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 || requestCode ==3){
            pagerAdapter.getSellFragment().onActivityResult(requestCode, resultCode, data);
        }else{
            pagerAdapter.getBuyFragment().onActivityResult(requestCode, resultCode, data);
        }
    }
}
