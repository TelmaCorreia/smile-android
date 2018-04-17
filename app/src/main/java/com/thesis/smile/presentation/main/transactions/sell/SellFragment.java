package com.thesis.smile.presentation.main.transactions.sell;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentSellBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.main.transactions.buy.BuyViewModel;

public class SellFragment extends BaseFragment<FragmentSellBinding, SellViewModel> {

    public static SellFragment newInstance() {
        SellFragment f = new SellFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_sell;
    }

    @Override
    protected Class<SellViewModel> viewModelClass() {
        return SellViewModel.class;
    }

    @Override
    protected void initViews(FragmentSellBinding binding) {

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }


}
