package com.thesis.smile.presentation.main.home.pager_fragments;

import android.widget.GridView;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentBatteryBinding;
import com.thesis.smile.databinding.FragmentTransactionsHomeBinding;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.main.home.HomeDetailsActivity;
import com.thesis.smile.presentation.utils.actions.events.Event;

public class TransactionsHomeFragment extends BaseFragment<FragmentTransactionsHomeBinding, TransactionsHomeViewModel> {

    private GridView gridViewBuy;
    private GridView gridViewBuyLegend;
    private GridView gridViewSell;
    private GridView gridViewSellLegend;
    private HeatMapAdapter heatMapBuyAdapter;
    private HeatMapLegendAdapter heatMapLegendAdapter;
    private HeatMapAdapter heatMapSellAdapter;

    public static TransactionsHomeFragment newInstance() {
        return new TransactionsHomeFragment();
    }

    @Override
    protected void initViews(FragmentTransactionsHomeBinding paramFragmentTransactionsHomeBinding) {

        this.gridViewBuy = paramFragmentTransactionsHomeBinding.gridviewbuy;
        this.gridViewSell = paramFragmentTransactionsHomeBinding.gridviewsell;
        this.gridViewBuyLegend = paramFragmentTransactionsHomeBinding.gridviewbuylegend;
        this.gridViewSellLegend = paramFragmentTransactionsHomeBinding.gridviewselllegend;

    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_transactions_home;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel()
                .observeOpenHomeBoughtDetails()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    HomeDetailsActivity.launch(getContext(), getResources().getString(R.string.details_bought_energy));
                });

        getViewModel()
                .observeOpenHomeSoldDetails()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    HomeDetailsActivity.launch(getContext(), getResources().getString(R.string.details_sold_energy));
                });
        getViewModel()
                .observeData()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::initGraph);

    }

    @Override
    protected Class<TransactionsHomeViewModel> viewModelClass() {
        return TransactionsHomeViewModel.class;
    }

    private void initGraph(Event paramEvent)
    {
        this.heatMapLegendAdapter = new HeatMapLegendAdapter(getContext(), (getViewModel()).getLegendList());
        if (this.heatMapBuyAdapter == null) {
            this.heatMapBuyAdapter = new HeatMapAdapter(getContext(), (getViewModel()).getCurrentEnergy().getBoughtList());
        }
        if (this.heatMapSellAdapter == null) {
            this.heatMapSellAdapter = new HeatMapAdapter(getContext(), (getViewModel()).getCurrentEnergy().getSoldList());
        }
        this.gridViewSell.setAdapter(this.heatMapSellAdapter);
        this.gridViewBuy.setAdapter(this.heatMapBuyAdapter);
        this.gridViewSellLegend.setAdapter(this.heatMapLegendAdapter);
        this.gridViewBuyLegend.setAdapter(this.heatMapLegendAdapter);
    }
}