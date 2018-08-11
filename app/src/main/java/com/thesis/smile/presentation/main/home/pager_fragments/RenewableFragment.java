package com.thesis.smile.presentation.main.home.pager_fragments;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentBatteryBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class TransactionsHomeFragment extends BaseFragment<FragmentBatteryBinding, BatteryViewModel> {
    public static TransactionsHomeFragment newInstance() {
        return new TransactionsHomeFragment();
    }

    @Override
    protected void initViews(FragmentBatteryBinding paramFragmentBatteryBinding) {}

    @Override
    protected int layoutResId() {
        return R.layout.fragment_battery;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();
    }

    protected Class<BatteryViewModel> viewModelClass() {
        return BatteryViewModel.class;
    }
}