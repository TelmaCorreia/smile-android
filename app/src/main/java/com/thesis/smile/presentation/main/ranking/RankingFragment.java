package com.thesis.smile.presentation.main.ranking;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentRankingBinding;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thesis.smile.domain.models.Ranking;
import com.thesis.smile.domain.models.RankingHeader;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;
import com.thesis.smile.presentation.main.home.HomeViewModel;
import com.thesis.smile.presentation.main.ranking.expandable_list.RankingAdapter;
import com.thesis.smile.presentation.user_expandable_list.NeighbourAdapter;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;

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
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration = new CustomItemDecoration(dividerDrawable); //FIXME item decoration
        LinearLayoutManager layoutManagerConsumer = new LinearLayoutManager(getContext());
        List<RankingHeader> rankingHeaders = getRanking();
        RankingAdapter adapter = new RankingAdapter(getContext(), rankingHeaders);
        binding.rankingList.setLayoutManager(layoutManagerConsumer);
        binding.rankingList.setAdapter(adapter);
        binding.rankingList.addItemDecoration(dividerItemDecoration);

    }

    public List<RankingHeader> getRanking() {
        List<RankingHeader> rankingHeaders = new ArrayList<>();
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking("Professor", "1º", "90% de energia solar", ""));
        rankings.add(new Ranking("Tókyo", "2º", "80% de energia solar", ""));
        rankings.add(new Ranking("Rio", "3º", "79% de energia solar", ""));
        rankings.add(new Ranking("Denver", "4º", "60% de energia solar", ""));
        rankings.add(new Ranking("Nairóbi", "5º", "40% de energia solar", ""));
        rankings.add(new Ranking("Oslo", "6º", "40% de energia solar", ""));
        rankings.add(new Ranking("Helsinque", "7º", "39.5% de energia solar", ""));
        rankings.add(new Ranking("Berlim", "8º", "37% de energia solar", ""));
        rankings.add(new Ranking("Moscou", "9º", "36% de energia solar", ""));
        rankings.add(new Ranking("Estocolmo", "10º", "30% de energia solar", ""));



        RankingHeader rankingHeader = new RankingHeader(getResources().getString(R.string.ranking_current_week_title), rankings);
        rankingHeaders.add(rankingHeader);
        RankingHeader rankingHeader1 = new RankingHeader(getResources().getString(R.string.ranking_previous_week_title), rankings);
        rankingHeaders.add(rankingHeader1);
        RankingHeader rankingHeader2 = new RankingHeader(getResources().getString(R.string.ranking_previous_week_title), rankings);
        rankingHeaders.add(rankingHeader2);

        return rankingHeaders;    }
}
