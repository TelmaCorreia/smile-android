package com.thesis.smile.presentation.user_expandable_list;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Neighbour;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class NeighbourChildViewHolder extends ChildViewHolder {

    public TextView name;
    public ImageView pic;
    public SwitchCompat switchCompat;

    public NeighbourChildViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.name);
        pic = (ImageView) itemView.findViewById(R.id.ivUser);
        switchCompat = (SwitchCompat) itemView.findViewById(R.id.switchCompact);

    }

    public void onBind(Neighbour neighbour) {
        name.setText(neighbour.getName());
        switchCompat.setChecked(neighbour.isVisible());
    }

    public void setName(TextView name) {
        this.name = name;
    }


    public void setPic(ImageView pic) {
        this.pic = pic;
    }

    public void setSwitchCompat(SwitchCompat switchCompat) {
        this.switchCompat = switchCompat;
    }
}
