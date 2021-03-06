package com.thesis.smile.presentation.main.historical.temporal_fragments;

import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
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
import com.thesis.smile.presentation.utils.views.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import static com.thesis.smile.Constants.ALPHA_ACTIVE;
import static com.thesis.smile.Constants.ALPHA_INACTIVE;
import static com.thesis.smile.Constants.BAR_SPACE;
import static com.thesis.smile.Constants.BORDER_WIDTH;
import static com.thesis.smile.Constants.DAILY_GROUP_SPACE;
import static com.thesis.smile.Constants.MAIN_BAR_WIDTH;
import static com.thesis.smile.Constants.SECONDARY_BAR_WIDTH;
import static com.thesis.smile.Constants.TOTALS_BAR_WIDTH;

public class DayHistoricalFragment extends BaseFragment<FragmentDayHistoricalBinding, DayHistoricalViewModel> {

    private BarChart barChart;
    private List<HistoricalDataPoint> list;

    CustomDialog dialog = null;

    private Boolean isStarted = false;
    private Boolean isVisible = false;

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

        getViewModel().observeSoldDetails()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::soldEnergyDialog);

        getViewModel().observeWasteDetails()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::wastedEnergyDialog);
    }

    @Override
    protected void initViews(FragmentDayHistoricalBinding binding) {
        this.barChart = binding.chart;

        barChart.setOnChartValueSelectedListener( new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (list!=null) {
                    HistoricalDataPoint dp = (HistoricalDataPoint) e.getData();
                    getViewModel().setCurrentDay(dp);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
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
        barChart.setHighlightFullBarEnabled(true);

        ArrayList<BarEntry> barAll = new ArrayList<BarEntry>();
        ArrayList<BarEntry> barSum = new ArrayList<BarEntry>();
        ArrayList<BarEntry> barConsumption = new ArrayList<BarEntry>();
        ArrayList<BarEntry> barProduction = new ArrayList<BarEntry>();

        int i = 0;

        float max =0;
        for (HistoricalDataPoint hdp: list){
            float temporary = (float) hdp.getEnergyBoughtNeighbours()+(float) hdp.getEnergyBoughtEem();
            if (temporary > max){
                max = temporary;
            }
        }
        for (HistoricalDataPoint hdp : list) {
            float surplus_sold = (float) hdp.getEnergySurplusNeighbours();
            float surplus_not_used = (float) hdp.getEnergySurplusNotUsed();
            float surplus = surplus_sold+surplus_not_used;
            float auto_consumption_from_battery = (float) hdp.getEnergyAutoConsumptionBattery();
            float auto_consumption_from_panels = (float) hdp.getEnergyAutoConsumptionPanels();
            float bought_neighbors = (float) hdp.getEnergyBoughtNeighbours();
            float bought_eem = (float) hdp.getEnergyBoughtEem();
            float hack = max - ((float) hdp.getEnergyBoughtNeighbours()+(float) hdp.getEnergyBoughtEem());
            barConsumption.add(new BarEntry(
                    i++,
                    new float[]{ hack, (float) hdp.getTotalConsumption() },
                    hdp, false));

            barAll.add(new BarEntry(
                    i++,
                    new float[]{ hack,
                                bought_eem,
                                bought_neighbors,
                                auto_consumption_from_panels,
                                auto_consumption_from_battery,
                                surplus_not_used,
                                surplus_sold},
                                hdp, true));
            barSum.add(new BarEntry(
                    i++,
                    new float[]{ hack,
                            bought_eem+bought_neighbors,
                            auto_consumption_from_panels + auto_consumption_from_battery,
                            surplus},
                            hdp, false));
            barProduction.add(new BarEntry(
                    i++,
                    new float[]{ hack, bought_eem+bought_neighbors, (float) hdp.getTotalProduction()},
                    hdp, false));

        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(15.4f);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        BarDataSet setAll;
        setAll = new BarDataSet(barAll, "");
        setAll.setDrawIcons(false);
        setAll.setColors(getColorsBarAll());
        setAll.setHighLightAlpha(ALPHA_ACTIVE);
        setAll.setDrawValues(false);
        setAll.setBarWidth(MAIN_BAR_WIDTH);

        BarDataSet setSum;
        setSum = new BarDataSet(barSum, "");
        setSum.setDrawIcons(false);
        setSum.setDrawValues(false);
        setSum.setColors(getColorsBarSum());
       // setSum.setBarBorderColor(getResources().getColor(R.color.colorBlack));
       // setSum.setBarBorderWidth(BORDER_WIDTH);
        setSum.setHighLightAlpha(ALPHA_ACTIVE);
        setSum.setBarWidth(SECONDARY_BAR_WIDTH);

        BarDataSet setConsumption;
        setConsumption = new BarDataSet(barConsumption, "");
        setConsumption.setDrawIcons(false);
        setConsumption.setDrawValues(false);
        setConsumption.setColors(getColorsBarConsumption());
        setConsumption.setHighLightAlpha(ALPHA_INACTIVE);
        setConsumption.setBarWidth(TOTALS_BAR_WIDTH);

        BarDataSet setProduction;
        setProduction = new BarDataSet(barProduction, "");
        setProduction.setDrawIcons(false);
        setProduction.setDrawValues(false);
        setProduction.setColors(getColorsBarProduction());
        setProduction.setHighLightAlpha(ALPHA_INACTIVE);
        setProduction.setBarWidth(TOTALS_BAR_WIDTH);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(setConsumption);
        dataSets.add(setAll);
        dataSets.add(setSum);
        dataSets.add(setProduction);

        BarData data = new BarData(dataSets);
        data.groupBars(0f, DAILY_GROUP_SPACE, BAR_SPACE);
        float position = getCurrentDayPosition(data.getGroupWidth(DAILY_GROUP_SPACE, BAR_SPACE));
        getViewModel().getCurrentDay();
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.highlightValue(position, 1);
        barChart.invalidate();
    }

    private int[] getColorsBarAll() {

        int[] colors = {getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.light_yellow),getResources().getColor(R.color.dark_yellow),
                getResources().getColor(R.color.light_pink),getResources().getColor(R.color.dark_pink),
                getResources().getColor(R.color.light_blue), getResources().getColor(R.color.dark_blue)};

        return colors;
    }

    private int[] getColorsBarSum() {

        int[] colors = {getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.outline_yellow),
                getResources().getColor(R.color.outline_pink),
                getResources().getColor(R.color.outline_blue)};

        return colors;
    }

    private int[] getColorsBarConsumption() {

        int[] colors = {getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorBlack)};

        return colors;
    }

    private int[] getColorsBarProduction() {

        int[] colors = {getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorWhite),
                getResources().getColor(R.color.colorGreen)};

        return colors;
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible && isStarted){
            viewDidAppear();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            viewDidAppear();
        }
    }

    public void viewDidAppear() {
       getViewModel().getHistoricalDataFromServer();
    }


    public float getCurrentDayPosition(float groupWidth) {
        String title = getViewModel().getCurrentDay().getTitle();
        float position = 0;
        for (HistoricalDataPoint dp: getViewModel().getCurrentData().getDataPoints()){
            if (dp.getTitle().equals(title)){
                break;
            }
            position+=1;
        }
        return position*groupWidth;
    }

    private void soldEnergyDialog(Event event) {
        dialog = new CustomDialog(getActivity());
        dialog.setTitle(R.string.sold_energy_details_tilte);
        dialog.setMessage(R.string.sold_energy_detais_description);
        dialog.setSecondMessage(R.string.sold_energy_details_info);
        dialog.setOkButtonText(R.string.button_ok);
        dialog.setDismissible(false);
        dialog.setOnOkClickListener(() -> {dialog.dismiss();});
        dialog.show();
    }

    private void wastedEnergyDialog(Event event) {
        String message = "";
        if (getViewModel().getCurrentDay().getWastedEnergy()>0){
            message = String.format("%.2f", getViewModel().getCurrentDay().getWastedEnergy()) + getResourceProvider().getString(R.string.wasted_energy_alert_value);
        }

        message +=getResourceProvider().getString(R.string.wasted_energy_alert_info);
        dialog = new CustomDialog(getActivity());
        dialog.setTitle(R.string.wasted_energy_alert_tilte);
        dialog.setMessage(R.string.wasted_energy_alert_description);
        dialog.setSecondMessage(message);
        dialog.setOkButtonText(R.string.button_ok);
        dialog.setDismissible(false);
        dialog.setOnOkClickListener(() -> {dialog.dismiss();});
        dialog.show();
    }


}
