package com.thesis.smile.presentation.main.transactions.expandable_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class NeighbourAdapter extends ExpandableRecyclerViewAdapter<NeighbourParentViewHolder,NeighbourChildViewHolder> {

    private LayoutInflater inflater;

    public NeighbourAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public NeighbourParentViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.list_item_group, viewGroup, false);
        return new NeighbourParentViewHolder(view);
    }

    @Override
    public NeighbourChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.list_item_user, viewGroup, false);
        return new NeighbourChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(NeighbourChildViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        final Neighbour neighbour = ((NeighbourHeader) group).getItems().get(childIndex);
        holder.onBind(neighbour);
    }

    @Override
    public void onBindGroupViewHolder(NeighbourParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group);
    }


}
