package com.thesis.smile.presentation.main.home;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHomeBinding;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;

public class HomeFragment extends BaseToolbarFragment<FragmentHomeBinding, HomeViewModel> {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeViewModel> viewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected void initViews(FragmentHomeBinding binding) {
        initToolbar(binding.actionBar.appBar, binding.actionBar.toolbar, false);
    }
}
