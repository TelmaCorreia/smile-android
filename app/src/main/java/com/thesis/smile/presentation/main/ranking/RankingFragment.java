package com.thesis.smile.presentation.main.ranking;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentRankingBinding;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;
import com.thesis.smile.presentation.main.home.HomeViewModel;

public class RankingFragment extends BaseToolbarFragment<FragmentRankingBinding, RankingViewModel> {

    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected Class<RankingViewModel> viewModelClass() {
        return RankingViewModel.class;
    }

    @Override
    protected void initViews(FragmentRankingBinding binding) {
        initToolbar(binding.actionBar.appBar, binding.actionBar.toolbar, false, getResources().getString(R.string.ranking_title));
    }
}
