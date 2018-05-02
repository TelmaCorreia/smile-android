package com.thesis.smile.presentation.main.transactions.buy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentBuyBinding;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.user_expandable_list.NeighbourAdapter;
import com.thesis.smile.presentation.info_price.InfoPriceActivity;
import com.thesis.smile.presentation.timers.timer_list.TimeIntervalAdapter;
import com.thesis.smile.presentation.timers.TimersActivity;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BuyFragment extends BaseFragment<FragmentBuyBinding, BuyViewModel> {

    static final int REQUEST_TIMERS = 2;
    static final int REQUEST_TIMERS_EDIT = 4;

    private static final String TIMER = "timer";

    public static BuyFragment newInstance() {
        BuyFragment f = new BuyFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_buy;
    }

    @Override
    protected Class<BuyViewModel> viewModelClass() {
        return BuyViewModel.class;
    }

    @Override
    protected void initViews(FragmentBuyBinding binding) {

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration = new CustomItemDecoration(dividerDrawable); //FIXME item decoration
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        TimeIntervalAdapter timeIntervalAdapter = new TimeIntervalAdapter(getViewModel().getTimeIntervals(),this::onTimeIntervalSelected,this::onRemoveTimeIntervalSelected);
        binding.timersBuy.setLayoutManager(layoutManager);
        binding.timersBuy.setAdapter(timeIntervalAdapter);
        binding.timersBuy.addItemDecoration(dividerItemDecoration);

    }

    private void onRemoveTimeIntervalSelected(TimeInterval timeInterval) {
        getViewModel().removeTimerInterval(timeInterval);
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeOpenPriceInfo()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    InfoPriceActivity.launch(getContext());
                });

        getViewModel().observeOpenTimer()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    TimersActivity.launchForResult(getActivity(), REQUEST_TIMERS, TIMER);
                });

        getViewModel().observeNeighbours()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::initNeighbours);

        getViewModel().observeBuySettings()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::updateViews);
    }

    private void updateViews(Event event) {
        getBinding().rbEemPrice.setChecked(getViewModel().getBuySettings().isEemPrice());
        getViewModel().setOption1(getViewModel().getBuySettings().isEemPrice());
        getBinding().rbEemPlusPrice.setChecked(getViewModel().getBuySettings().isEemPlusPrice());
        getViewModel().setOption2(getViewModel().getBuySettings().isEemPlusPrice());
    }

    private void initNeighbours(Event event) {
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration_ = new CustomItemDecoration(dividerDrawable); //FIXME item decoration
        List<NeighbourHeader> neighbourHeaders = getSuppliers();
        LinearLayoutManager layoutManagerSup = new LinearLayoutManager(getContext());
        NeighbourAdapter adapter = new NeighbourAdapter(getContext(), neighbourHeaders);
        getBinding().suppliers.setLayoutManager(layoutManagerSup);
        getBinding().suppliers.setAdapter(adapter);
        getBinding().suppliers.addItemDecoration(dividerItemDecoration_);
    }

    private void onTimeIntervalSelected(TimeInterval timeInterval) {
        TimersActivity.launchForResult(getActivity(),REQUEST_TIMERS_EDIT, TIMER, timeInterval);
    }

    public List<NeighbourHeader> getSuppliers() {
        List<NeighbourHeader> neighbourHeaders = new ArrayList<>();
        List<Neighbour> neighbours = new ArrayList<>();
        neighbours.add(new Neighbour("0","Selecionar todos", getViewModel().isAllNeighboursSelected(), false));
        neighbours.addAll(getViewModel().getNeighbours());
        NeighbourHeader neighbourHeader = new NeighbourHeader(getResources().getString(R.string.consumers_title), getResources().getString(R.string.consumers_description), neighbours);
        neighbourHeaders.add(neighbourHeader);
        return neighbourHeaders;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TIMERS && resultCode == Activity.RESULT_OK && data.getExtras() != null){
            if(data != null && data.getExtras() != null){
                TimeInterval timeInterval = data.getExtras().getParcelable(TIMER);
                getViewModel().addTimeInterval(timeInterval, false);
            }
        }else if (requestCode == REQUEST_TIMERS_EDIT && resultCode == Activity.RESULT_OK && data.getExtras() != null){
            TimeInterval timeInterval = data.getExtras().getParcelable(TIMER);
            getViewModel().addTimeInterval(timeInterval, true);
        }
    }
}
