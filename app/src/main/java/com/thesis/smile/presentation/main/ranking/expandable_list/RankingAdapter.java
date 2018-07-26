package com.thesis.smile.presentation.main.ranking.expandable_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Ranking;
import com.thesis.smile.domain.models.RankingHeader;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RankingAdapter extends ExpandableRecyclerViewAdapter<RankingParentViewHolder,RankingChildViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    public RankingAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RankingParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_group, parent, false);
        return new RankingParentViewHolder(view);    }

    @Override
    public RankingChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_ranking, parent, false);
        return new RankingChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(RankingChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Ranking ranking = ((RankingHeader) group).getItems().get(childIndex);
        holder.onBind(ranking,context );
    }

    @Override
    public void onBindGroupViewHolder(RankingParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.onBind(group);
    }

}
