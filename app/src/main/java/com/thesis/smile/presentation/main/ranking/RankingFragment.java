package com.thesis.smile.presentation.main.ranking;

import android.support.v7.widget.LinearLayoutManager;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentRankingBinding;
import com.thesis.smile.domain.models.Ranking;
import com.thesis.smile.domain.models.RankingHeader;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.main.ranking.expandable_list.RankingAdapter;
import com.thesis.smile.presentation.utils.actions.events.Event;

import java.util.ArrayList;
import java.util.List;

public class RankingFragment extends BaseFragment<FragmentRankingBinding, RankingViewModel> {

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
        //initRankings(new Event());
    }


    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeRankings()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::initRankings);

    }

    private void initRankings(Event event) {

        LinearLayoutManager layoutManagerConsumer = new LinearLayoutManager(getContext());
        List<RankingHeader> rankingHeaders = getViewModel().getRanking();
        RankingAdapter adapter = new RankingAdapter(getContext(), rankingHeaders);
        adapter.toggleGroup(0);
        getBinding().rankingList.setLayoutManager(layoutManagerConsumer);
        getBinding().rankingList.setAdapter(adapter);
    }
}
