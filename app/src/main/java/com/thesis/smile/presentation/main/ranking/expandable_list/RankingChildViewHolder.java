package com.thesis.smile.presentation.main.ranking.expandable_list;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thesis.smile.R;
import com.thesis.smile.domain.models.Ranking;
import com.thesis.smile.presentation.main.MainActivity;
import com.thesis.smile.presentation.main.ranking.RankingFragment;
import com.thesis.smile.presentation.utils.databinding.ImageBindings;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class RankingChildViewHolder extends ChildViewHolder{

    public TextView position;
    public TextView name;
    public TextView description;
    public ImageView pic;

    public RankingChildViewHolder(View itemView) {
        super(itemView);
        position = (TextView) itemView.findViewById(R.id.position);
        name = (TextView) itemView.findViewById(R.id.name);
        description = (TextView) itemView.findViewById(R.id.percentage);
        pic = (ImageView) itemView.findViewById(R.id.ivUser);

    }

    public void onBind(Ranking ranking, Context context) {
        position.setText(ranking.getPosition());
        name.setText(ranking.getName());
        if (ranking.getName().contains("tu")){
            name.setTextColor(context.getResources().getColor(R.color.colorUnderline));
            position.setTextColor(context.getResources().getColor(R.color.colorUnderline));
        }
        description.setText(ranking.getDescription());
        ImageBindings.setImageCircleUrl(pic, ranking.getPicture(), context.getResources().getDrawable(R.drawable.ic_person));

    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setPosition(TextView position) {
        this.name = position;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public void setPic(ImageView pic) {
        this.pic = pic;
    }
}
