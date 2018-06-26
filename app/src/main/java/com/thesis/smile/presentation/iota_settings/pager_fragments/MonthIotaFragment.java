package com.thesis.smile.presentation.iota_settings.pager_fragments;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentMonthIotaBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class MonthIotaFragment extends BaseFragment<FragmentMonthIotaBinding, MonthIotaViewModel> {

    public static MonthIotaFragment newInstance() {
        MonthIotaFragment f = new MonthIotaFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_month_iota;
    }

    @Override
    protected Class<MonthIotaViewModel> viewModelClass() {
        return MonthIotaViewModel.class;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }

    @Override
    protected void initViews(FragmentMonthIotaBinding binding) {

    }




}
