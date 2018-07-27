package com.thesis.smile.presentation.user_expandable_list;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.presentation.utils.databinding.ImageBindings;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class NeighbourChildViewHolder extends ChildViewHolder {

    public TextView name;
    public ImageView pic;
    public SwitchCompat switchCompat;
    private NeighbourAdapter.OnSwitchClickListener onSwitchClickListener;
    private Context context;

    public NeighbourChildViewHolder(View itemView, NeighbourAdapter.OnSwitchClickListener onSwitchClickListener, Context context) {
        super(itemView);
        this.context = context;
        name = (TextView) itemView.findViewById(R.id.name);
        pic = (ImageView) itemView.findViewById(R.id.ivUser);
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
        ImageBindings.setImageCircleUrl(pic, neighbour.getUrl(), context.getResources().getDrawable(R.drawable.ic_person));

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
