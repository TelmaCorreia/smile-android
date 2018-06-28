package com.thesis.smile.presentation.main.historical.temporal_fragments;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentDayHistoricalBinding;
import com.thesis.smile.databinding.FragmentDayIotaBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.iota_settings.pager_fragments.DayIotaViewModel;

import java.util.ArrayList;

public class DayHistoricalFragment extends BaseFragment<FragmentDayHistoricalBinding, DayHistoricalViewModel> implements OnChartValueSelectedListener {

    public static DayHistoricalFragment newInstance() {
        DayHistoricalFragment f = new DayHistoricalFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_day_historical;
    }

    @Override
    protected Class<DayHistoricalViewModel> viewModelClass() {
        return DayHistoricalViewModel.class;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }

    @Override
    protected void initViews(FragmentDayHistoricalBinding binding) {
        binding.chart.setOnChartValueSelectedListener(this);
        binding.chart.getDescription().setEnabled(false);
        binding.chart.setDrawGridBackground(false);
        binding.chart.setDrawBarShadow(false);

        XAxis xAxis = binding.chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        XAxis xLabels = binding.chart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 6 + 1; i++) {
            float surplus_sold = (float) 2.7;
            float surplus_not_used = (float) 1.6;
            float auto_consumption_from_battery = (float) 5.2;
            float auto_consumption_from_panels = (float) 2.1;
            float bought_neighbors = (float) 1.4;
            float bought_eem = (float) 2.5;
            yVals1.add(new BarEntry(
                    i,
                    new float[]{surplus_sold, surplus_not_used, auto_consumption_from_battery, auto_consumption_from_panels, bought_neighbors, bought_eem},
                    getResources().getDrawable(R.drawable.ic_add)));
        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "");
        set1.setDrawIcons(false);
        set1.setColors(getColors());

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        binding.chart.setData(data);

        binding.chart.setFitBars(true);
        binding.chart.invalidate();

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private int[] getColors() {

        int[] colors = {getResources().getColor(R.color.light_yellow),getResources().getColor(R.color.dark_yellow),
                        getResources().getColor(R.color.light_pink),getResources().getColor(R.color.dark_pink),
                        getResources().getColor(R.color.light_blue), getResources().getColor(R.color.dark_blue)};

        return colors;
    }
}
