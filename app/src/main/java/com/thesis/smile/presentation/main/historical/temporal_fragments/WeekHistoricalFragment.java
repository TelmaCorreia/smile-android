package com.thesis.smile.presentation.main.historical.temporal_fragments;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentMonthHistoricalBinding;
import com.thesis.smile.databinding.FragmentWeekHistoricalBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class WeekHistoricalFragment extends BaseFragment<FragmentWeekHistoricalBinding, WeekHistoricalViewModel> {

    public static WeekHistoricalFragment newInstance() {
        WeekHistoricalFragment f = new WeekHistoricalFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_week_historical;
    }

    @Override
    protected Class<WeekHistoricalViewModel> viewModelClass() {
        return WeekHistoricalViewModel.class;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }

    @Override
    protected void initViews(FragmentWeekHistoricalBinding binding) {

    }




}
