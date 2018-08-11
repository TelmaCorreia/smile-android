package com.thesis.smile.presentation.main.home.pager_fragments;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentBatteryBinding;
import com.thesis.smile.databinding.FragmentRenewableBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.utils.actions.events.Event;

import java.util.ArrayList;
import java.util.List;

public class RenewableFragment extends BaseFragment<FragmentRenewableBinding, RenewableViewModel> {

    private PieChart pieChart;

    public static RenewableFragment newInstance() {
        return new RenewableFragment();
    }

    @Override
    protected void initViews(FragmentRenewableBinding paramFragmentRenewableBinding) {

        this.pieChart = paramFragmentRenewableBinding.pieChart;
        if ((getViewModel()).getCurrentEnergy() != null)
        {
            this.pieChart.setUsePercentValues(true);
            this.pieChart.getDescription().setEnabled(false);
            this.pieChart.setDrawCenterText(true);
            this.pieChart.setCenterTextSize(18.0F);
            this.pieChart.setDrawEntryLabels(false);
            this.pieChart.setCenterText(((RenewableViewModel)getViewModel()).getTotalSolarEnergy());
            this.pieChart.setHoleRadius(85.0F);
            setData();
            this.pieChart.getLegend().setEnabled(false);
            this.pieChart.animateY(1400, Easing.EaseInOutQuad);
        }

    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_renewable;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel()
                .observeData()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::initGraph);
    }

    protected Class<RenewableViewModel> viewModelClass() {
        return RenewableViewModel.class;
    }

    private void setData()
    {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry((getViewModel()).getValue(), ""));
        entries.add(new PieEntry(100.0F - (getViewModel()).getValue(), ""));
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0.0F);
        dataSet.setDrawValues(false);
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(Integer.valueOf(getResources().getColor(R.color.colorGreen)));
        localArrayList.add(Integer.valueOf(getResources().getColor(R.color.colorDividerGrey)));
        dataSet.setColors(localArrayList);
        dataSet.setHighlightEnabled(false);
        PieData data = new PieData(dataSet);
        this.pieChart.setData(data);
        this.pieChart.invalidate();
    }

    public void initGraph(Event paramEvent)
    {
        this.pieChart.setUsePercentValues(true);
        this.pieChart.getDescription().setEnabled(false);
        this.pieChart.setDrawCenterText(true);
        this.pieChart.setCenterTextSize(18.0F);
        this.pieChart.setDrawEntryLabels(false);
        this.pieChart.setCenterText(((RenewableViewModel)getViewModel()).getTotalSolarEnergy());
        this.pieChart.setHoleRadius(85.0F);
        setData();
        this.pieChart.getLegend().setEnabled(false);
        this.pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

}