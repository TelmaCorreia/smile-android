package com.thesis.smile.presentation.iota_settings.pager_fragments;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentWeekIotaBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class WeekIotaFragment extends BaseFragment<FragmentWeekIotaBinding, WeekIotaViewModel> {

    public static WeekIotaFragment newInstance() {
        WeekIotaFragment f = new WeekIotaFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_week_iota;
    }

    @Override
    protected Class<WeekIotaViewModel> viewModelClass() {
        return WeekIotaViewModel.class;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }

    @Override
    protected void initViews(FragmentWeekIotaBinding binding) {

    }




}
