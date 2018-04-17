package com.thesis.smile.presentation.main.transactions.historical_transactions;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHistoricalTransactionsBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class HistoricalTransactionsFragment extends BaseFragment<FragmentHistoricalTransactionsBinding, HistoricalTransactionsViewModel> {

    public static HistoricalTransactionsFragment newInstance() {
        HistoricalTransactionsFragment f = new HistoricalTransactionsFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_historical_transactions;
    }

    @Override
    protected Class<HistoricalTransactionsViewModel> viewModelClass() {
        return HistoricalTransactionsViewModel.class;
    }

    @Override
    protected void initViews(FragmentHistoricalTransactionsBinding binding) {

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }


}
