package com.thesis.smile.presentation.iota_settings.pager_fragments;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentDayIotaBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class DayIotaFragment extends BaseFragment<FragmentDayIotaBinding, DayIotaViewModel> {

    public static DayIotaFragment newInstance() {
        DayIotaFragment f = new DayIotaFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_day_iota;
    }

    @Override
    protected Class<DayIotaViewModel> viewModelClass() {
        return DayIotaViewModel.class;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }

    @Override
    protected void initViews(FragmentDayIotaBinding binding) {

    }




}
