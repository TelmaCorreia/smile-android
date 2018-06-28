package com.thesis.smile.presentation.main.historical.temporal_fragments;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentDayHistoricalBinding;
import com.thesis.smile.databinding.FragmentMonthHistoricalBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class MonthHistoricalFragment extends BaseFragment<FragmentMonthHistoricalBinding, MonthHistoricalViewModel> {

    public static MonthHistoricalFragment newInstance() {
        MonthHistoricalFragment f = new MonthHistoricalFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_month_historical;
    }

    @Override
    protected Class<MonthHistoricalViewModel> viewModelClass() {
        return MonthHistoricalViewModel.class;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }

    @Override
    protected void initViews(FragmentMonthHistoricalBinding binding) {

    }




}
