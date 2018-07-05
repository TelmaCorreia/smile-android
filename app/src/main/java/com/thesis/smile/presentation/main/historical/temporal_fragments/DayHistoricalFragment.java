package com.thesis.smile.presentation.main.historical.temporal_fragments;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentDayHistoricalBinding;
import com.thesis.smile.domain.models.HistoricalDataPoint;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.utils.actions.events.Event;

import java.util.ArrayList;
import java.util.List;

public class DayHistoricalFragment extends BaseFragment<FragmentDayHistoricalBinding, DayHistoricalViewModel> {

    private BarChart barChart;
    private List<HistoricalDataPoint> list;

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

        getViewModel().observeHistoricalData()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::initData);
    }


    @Override
    protected void initViews(FragmentDayHistoricalBinding binding) {
        this.barChart = binding.chart;

        barChart.setOnChartValueSelectedListener( new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (list!=null){
                    HistoricalDataPoint dp = (HistoricalDataPoint) e.getData();
                    getViewModel().setCurrentDay(dp);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }


    private int[] getColorsBarAll() {

        int[] colors = {getResources().getColor(R.color.light_yellow),getResources().getColor(R.color.dark_yellow),
                getResources().getColor(R.color.light_pink),getResources().getColor(R.color.dark_pink),
                getResources().getColor(R.color.light_blue), getResources().getColor(R.color.dark_blue)};

        return colors;
    }

    private int[] getColorsBar1() {

        int[] colors = {getResources().getColor(R.color.outline_yellow),
                        getResources().getColor(R.color.outline_pink),
                        getResources().getColor(R.color.outline_blue)};

        return colors;
    }


    private void initData(Event event) {
        this.list = getViewModel().getCurrentData().getDataPoints();
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setHighlightFullBarEnabled(false);


        /*XAxis xLabels = barChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);*/

        ArrayList<BarEntry> barAll = new ArrayList<BarEntry>();
        ArrayList<BarEntry> bar1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> bar2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> bar3 = new ArrayList<BarEntry>();

        final ArrayList<String> xAxes = new ArrayList<>();
        int i = 0;
        for (HistoricalDataPoint hdp : list) {
            float surplus_sold = (float) hdp.getEnergySurplusNeighbours();
            float surplus_not_used = (float) hdp.getEnergySurplusNotUsed();
            float auto_consumption_from_battery = (float) hdp.getEnergyAutoConsumptionBattery();
            float auto_consumption_from_panels = (float) hdp.getEnergyAutoConsumptionPanels();
            float bought_neighbors = (float) hdp.getEnergyBoughtNeighbours();
            float bought_eem = (float) hdp.getEnergyBoughtEem();
            xAxes.add(i, hdp.getTitle()); //Dynamic x-axis labels
            barAll.add(new BarEntry(
                    i++,
                    new float[]{ bought_eem,
                                bought_neighbors,
                                auto_consumption_from_panels,
                                auto_consumption_from_battery,
                                surplus_not_used,
                                surplus_sold},
                                getResources().getDrawable(R.drawable.ic_add),
                                hdp));
            xAxes.add(i, hdp.getTitle()); //Dynamic x-axis labels
            bar1.add(new BarEntry(
                    i++,
                    new float[]{ bought_eem+bought_neighbors,
                            auto_consumption_from_panels + auto_consumption_from_battery,
                            surplus_not_used+surplus_sold},
                            getResources().getDrawable(R.drawable.ic_add),
                            hdp));
            




        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter((value, axis) -> {
            int index = (int) value;
            return xAxes.get(index);
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        BarDataSet set1;
        set1 = new BarDataSet(barAll, "");
        set1.setDrawIcons(false);
        set1.setColors(getColorsBarAll());
        set1.setHighLightAlpha(0);
        set1.setDrawValues(false);


        BarDataSet set2;
        set2 = new BarDataSet(bar1, "");
        set2.setDrawIcons(false);
        set2.setDrawValues(false);
        set2.setColors(getColorsBar1());
        set2.setHighLightAlpha(0);
        set2.setBarWidth(0.40f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        data.groupBars(-.8f, 0.3f, 0f);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
}
