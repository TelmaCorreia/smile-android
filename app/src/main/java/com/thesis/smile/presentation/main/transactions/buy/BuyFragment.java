package com.thesis.smile.presentation.main.transactions.buy;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentBuyBinding;
import com.thesis.smile.presentation.base.BaseFragment;

public class BuyFragment extends BaseFragment<FragmentBuyBinding, BuyViewModel> {

    public static BuyFragment newInstance() {
        BuyFragment f = new BuyFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_buy;
    }

    @Override
    protected Class<BuyViewModel> viewModelClass() {
        return BuyViewModel.class;
    }

    @Override
    protected void initViews(FragmentBuyBinding binding) {

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }


}
