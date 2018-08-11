package com.thesis.smile.presentation.main.home.pager_fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.smile.R;

import java.util.List;

public class HeatMapLegendAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> list;

    public HeatMapLegendAdapter(Context paramContext, List<String> paramList)
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
            paramViewGroup = (ViewGroup) LayoutInflater.from(this.context).inflate(R.layout.layout_heatmap_text, null);
        }

        ((TextView)paramViewGroup.findViewById(R.id.tv)).setText(this.list.get(paramInt));
        return paramViewGroup;
    }
}

