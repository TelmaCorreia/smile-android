package com.thesis.smile.presentation.main.home.pager_fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.thesis.smile.R;

import java.util.List;

public class HeatMapAdapter extends BaseAdapter {
    private final Context context;
    private final List<Integer> list;

    public HeatMapAdapter(Context paramContext, List<Integer> paramList)
    {
        this.context = paramContext;
        this.list = paramList;
    }

    @Nullable
    public CharSequence[] getAutofillOptions()
    {
        return new CharSequence[0];
    }

    public int getCount()
    {
        return 24;
    }

    public Object getItem(int paramInt)
    {
        return null;
    }

    public long getItemId(int paramInt)
    {
        return 0L;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        paramViewGroup = (ViewGroup) paramView;
        if (paramView == null) {
            paramViewGroup = (ViewGroup) LayoutInflater.from(this.context).inflate(R.layout.layout_heatmap, null);
        }
        paramView = (ImageView)paramViewGroup.findViewById(R.id.iv);
        if (((Integer)this.list.get(paramInt)).intValue() == 1)
        {
            paramView.setBackgroundColor(this.context.getResources().getColor(R.color.colorZero));
            return paramViewGroup;
        }
        if (((Integer)this.list.get(paramInt)).intValue() == 2)
        {
            paramView.setBackgroundColor(this.context.getResources().getColor(R.color.colorOne));
            return paramViewGroup;
        }
        if (((Integer)this.list.get(paramInt)).intValue() == 3)
        {
            paramView.setBackgroundColor(this.context.getResources().getColor(R.color.colorTwo));
            return paramViewGroup;
        }
        if (((Integer)this.list.get(paramInt)).intValue() == 4)
        {
            paramView.setBackgroundColor(this.context.getResources().getColor(R.color.colorThree));
            return paramViewGroup;
        }
        if (((Integer)this.list.get(paramInt)).intValue() == 5)
        {
            paramView.setBackgroundColor(this.context.getResources().getColor(R.color.colorFour));
            return paramViewGroup;
        }
        paramView.setBackgroundColor(this.context.getResources().getColor(R.color.colorWhite));
        return paramViewGroup;
    }
}

