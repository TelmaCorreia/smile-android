package com.thesis.smile.presentation.user_expandable_list;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Neighbour;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class SelectAllChildViewHolder extends ChildViewHolder {

    public TextView name;
    public ImageView pic;
    public SwitchCompat switchCompat;
    private NeighbourAdapter.OnSwitchClickListener onSwitchClickListener;

    public SelectAllChildViewHolder(View itemView, NeighbourAdapter.OnSwitchClickListener onSwitchClickListener) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.name);
        pic = (ImageView) itemView.findViewById(R.id.ivUser);
        pic.setVisibility(View.INVISIBLE);
        switchCompat = (SwitchCompat) itemView.findViewById(R.id.switchCompact);
        this.onSwitchClickListener = onSwitchClickListener;

    }

    public void onBind(Neighbour neighbour) {
        name.setText(neighbour.getName());
        switchCompat.setChecked(neighbour.isBlocked());
        switchCompat.setOnClickListener(view -> {
            if (onSwitchClickListener!=null){
                onSwitchClickListener.onSwichClick(neighbour);
            }
        });
    }


}
