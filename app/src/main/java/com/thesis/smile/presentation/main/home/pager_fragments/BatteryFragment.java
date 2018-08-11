package com.thesis.smile.presentation.main.home.pager_fragments;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentBatteryBinding;
import com.thesis.smile.databinding.FragmentHomeBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.main.home.HomeDetailsActivity;
import com.thesis.smile.presentation.main.home.HomeViewModel;

public class BatteryFragment extends BaseFragment<FragmentBatteryBinding, BatteryViewModel> {
    public static BatteryFragment newInstance() {
        return new BatteryFragment();
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

    @Override
    protected Class<BatteryViewModel> viewModelClass() {
        return BatteryViewModel.class;
    }
}