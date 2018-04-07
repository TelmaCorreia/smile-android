package com.thesis.smile.presentation.main.transactions;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentTransactionsBinding;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;
import com.thesis.smile.presentation.main.home.HomeViewModel;

public class TransactionsFragment extends BaseToolbarFragment<FragmentTransactionsBinding, TransactionsViewModel> {

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

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
        initToolbar(binding.actionBar.appBar, binding.actionBar.toolbar, false, getResources().getString(R.string.transactions_title));
    }
}
