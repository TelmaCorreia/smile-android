package com.thesis.smile.presentation.main.historical;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHistoricalBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class HistoricalFragment extends BaseFragment<FragmentHistoricalBinding, HistoricalViewModel> {

    private final String TAG = HistoricalFragment.class.getCanonicalName();

    public static HistoricalFragment newInstance() {
        return new HistoricalFragment();
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_historical;
    }

    @Override
    protected Class<HistoricalViewModel> viewModelClass() {
        return HistoricalViewModel.class;
    }

    @Override
    protected void initViews(FragmentHistoricalBinding binding) {
        //initToolbar(binding.actionBar.appBar, binding.actionBar.toolbar, false, getResources().getString(R.string.historical_title));
    }



}
