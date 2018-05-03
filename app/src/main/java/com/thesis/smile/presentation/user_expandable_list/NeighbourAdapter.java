package com.thesis.smile.presentation.user_expandable_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thesis.smile.domain.models.TimeInterval;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.List;

public class NeighbourAdapter extends MultiTypeExpandableRecyclerViewAdapter<NeighbourParentViewHolder,ChildViewHolder> {

    public interface OnSwitchClickListener {
        void onSwichClick(Neighbour neighbour);
    }

    private LayoutInflater inflater;
    public static final int SELECT_ALL_VIEW_TYPE = 3;
    public static final int NEIGHBOUR_VIEW_TYPE = 4;
    /** Use values > 2. That's because ExpandableListPosition.CHILD and ExpandableListPositon.GROUP are 1 and 2 respectively so they are already taken.**/

    private OnSwitchClickListener onSwichClickListener;

    public NeighbourAdapter(Context context, List<? extends ExpandableGroup> groups, OnSwitchClickListener onSwichClickListener) {
        super(groups);
        this.onSwichClickListener = onSwichClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NeighbourParentViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int viewType) {
        View selectAll = inflater.inflate(R.layout.list_item_group, viewGroup, false);
        return new NeighbourParentViewHolder(selectAll);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case SELECT_ALL_VIEW_TYPE:
                View selectAll = inflater.inflate(R.layout.list_item_user, viewGroup, false);
                return new SelectAllChildViewHolder(selectAll, onSwichClickListener);
            case NEIGHBOUR_VIEW_TYPE:
                View neighbour =  inflater.inflate(R.layout.list_item_user, viewGroup, false);
                return new NeighbourChildViewHolder(neighbour, onSwichClickListener);
            default:
                throw new IllegalArgumentException("Invalid viewType");
        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {

        int viewType = getItemViewType(flatPosition);
        Neighbour neighbour = ((NeighbourHeader) group).getItems().get(childIndex);
        switch (viewType) {
            case SELECT_ALL_VIEW_TYPE:
                ((SelectAllChildViewHolder) holder).onBind(neighbour);
                break;
            case NEIGHBOUR_VIEW_TYPE:
                ((NeighbourChildViewHolder) holder).onBind(neighbour);
        }
    }

    @Override
    public void onBindGroupViewHolder(NeighbourParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group);
    }

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (((NeighbourHeader) group).getItems().get(childIndex).isSelectAll()) {
            return SELECT_ALL_VIEW_TYPE;
        } else {
            return NEIGHBOUR_VIEW_TYPE;
        }
    }

    @Override
    public boolean isChild(int viewType) {
        return viewType == SELECT_ALL_VIEW_TYPE || viewType == NEIGHBOUR_VIEW_TYPE;
    }


}
