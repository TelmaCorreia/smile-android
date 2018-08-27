package com.thesis.smile.presentation.main.home;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHomeBinding;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.domain.models.Users;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;
import com.thesis.smile.presentation.base.toolbar.ToolbarActionType;
import com.thesis.smile.presentation.main.home.super_user_list.UsersAdapter;
import com.thesis.smile.presentation.timers.timer_list.TimeIntervalAdapter;
import com.thesis.smile.presentation.utils.actions.events.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    private LineChart lineChart;
    HomePagerAdapter pagerAdapter;
    private UsersAdapter usersAdapterAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeViewModel> viewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected void initViews(FragmentHomeBinding binding) {
        this.pagerAdapter = new HomePagerAdapter(getChildFragmentManager(), getResourceProvider());
        binding.viewpager.setAdapter(this.pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewpager);
        this.lineChart = binding.lineChart;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        usersAdapterAdapter = new UsersAdapter(getViewModel().getUsers(), this::onUsersSelected);
        binding.usersrv.setLayoutManager(layoutManager);
        binding.usersrv.setAdapter(usersAdapterAdapter);

    }

    private void onUsersSelected(Users users) {
        getViewModel().updateToken(users.getToken());

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();
        getViewModel()
                .observeData()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::initGraph);

    }

    private void setData()
    {
        List<Entry> productionList = new ArrayList();
        List<Entry> consumptionList = new ArrayList();
        List<Entry> entries = new ArrayList();
        int i = 0;
        while (i < 24)
        {
            entries.add(new Entry(i, 0.0F));
            i += 1;
        }
        List<Double> productionL = getViewModel().getCurrentEnergy().getProductionList();
        List<Double> consumptionL = getViewModel().getCurrentEnergy().getConsumptionList();

        i=0;
        for (Double d:productionL){
            productionList.add(new Entry(i, (float)d.doubleValue()));
            i++;
        }

        i=0;
        for (Double d:consumptionL){
            consumptionList.add(new Entry(i, (float)d.doubleValue()));
            i++;
        }

        LineDataSet productionDataSet = new LineDataSet(productionList, "Produção");
        productionDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        productionDataSet.setColor(getResources().getColor(R.color.colorProd));
        productionDataSet.setValueTextColor(ColorTemplate.getHoloBlue());
        productionDataSet.setLineWidth(3.0F);
        productionDataSet.setDrawCircles(false);
        productionDataSet.setDrawValues(false);
        productionDataSet.setFillAlpha(65);
        productionDataSet.setFillColor(ColorTemplate.getHoloBlue());
        productionDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        productionDataSet.setDrawCircleHole(false);
        productionDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineDataSet consumptionDataSet = new LineDataSet((List)consumptionList, "Consumo");
        consumptionDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        consumptionDataSet.setColor(getResources().getColor(R.color.colorCons));
        consumptionDataSet.setValueTextColor(ColorTemplate.getHoloBlue());
        consumptionDataSet.setLineWidth(3.0F);
        consumptionDataSet.setDrawCircles(false);
        consumptionDataSet.setDrawValues(false);
        consumptionDataSet.setFillAlpha(65);
        consumptionDataSet.setFillColor(ColorTemplate.getHoloBlue());
        consumptionDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        consumptionDataSet.setDrawCircleHole(false);
        consumptionDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineDataSet zeroDataset = new LineDataSet((List)entries, "");
        zeroDataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        zeroDataset.setColor(getResources().getColor(R.color.colorWhite));
        zeroDataset.setValueTextColor(ColorTemplate.getHoloBlue());
        zeroDataset.setLineWidth(3.0F);
        zeroDataset.setDrawCircles(false);
        zeroDataset.setDrawValues(false);
        zeroDataset.setFillAlpha(65);
        zeroDataset.setFillColor(ColorTemplate.getHoloBlue());
        zeroDataset.setHighLightColor(Color.rgb(244, 117, 117));
        zeroDataset.setDrawCircleHole(false);
        zeroDataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData data = new LineData(new ILineDataSet[] { zeroDataset, productionDataSet, consumptionDataSet });
        data.setValueTextSize(9.0F);
        this.lineChart.setData(data);
    }

    public void initGraph(Event paramEvent)
    {
        this.lineChart.setBackgroundColor(Color.WHITE);
        this.lineChart.setDrawGridBackground(false);
        this.lineChart.setViewPortOffsets(0.0F, 0.0F, 0.0F, 0.0F);
        this.lineChart.getDescription().setEnabled(false);
        this.lineChart.setDragEnabled(false);
        this.lineChart.setTouchEnabled(true);
        this.lineChart.setPinchZoom(false);
        this.lineChart.setScaleEnabled(false);
        setData();
        this.lineChart.invalidate();
        this.lineChart.getLegend().setEnabled(false);
        this.lineChart.getAxisLeft().setEnabled(false);
        this.lineChart.getAxisLeft().setSpaceTop(40.0F);
        this.lineChart.getAxisLeft().setSpaceBottom(40.0F);
        this.lineChart.getAxisRight().setEnabled(false);
        this.lineChart.getXAxis().setEnabled(false);
        this.lineChart.getXAxis().setDrawLabels(true);
       // this.lineChart.getXAxis().setTextColor(-16777216);
       // this.lineChart.getXAxis().setValueFormatter(eFormatter(HomeFragment..Lambda.2.$instance);
        this.lineChart.animateX(1500);
    }


}
