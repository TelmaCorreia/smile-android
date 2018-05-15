package com.thesis.smile.presentation.user_expandable_list;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class NeighbourParentViewHolder extends GroupViewHolder {

    public TextView title;
    public TextView subtitle;
    private ImageView arrow;


    public NeighbourParentViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.header);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        arrow = (ImageView) itemView.findViewById(R.id.bt_expand);

    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);

    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    public void onBind(ExpandableGroup group) {
        this.title.setText(group.getTitle());
        this.subtitle.setText(((NeighbourHeader) group).getSubtitle());
        if (group.getTitle().isEmpty()){
            arrow.setVisibility(View.INVISIBLE);
            title.setVisibility(View.GONE);
        }

    }
}
