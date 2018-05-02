package com.thesis.smile.presentation.main.transactions.sell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.SeekBar;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentSellBinding;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class SellFragment extends BaseFragment<FragmentSellBinding, SellViewModel> {

    static final int REQUEST_TIMERS = 1;
    static final int REQUEST_TIMERS_EDIT = 3;
    private static final String TIMER = "timer";

    public static SellFragment newInstance() {
        SellFragment f = new SellFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_sell;
    }

    @Override
    protected Class<SellViewModel> viewModelClass() {
        return SellViewModel.class;
    }

    @Override
    protected void initViews(FragmentSellBinding binding) {

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration = new CustomItemDecoration(dividerDrawable); //FIXME item decoration
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        TimeIntervalAdapter timeIntervalAdapter = new TimeIntervalAdapter(getViewModel().getTimeIntervals(), this::onTimeIntervalSelected, this::onRemoveTimeIntervalSelected);
        binding.timersSell.setLayoutManager(layoutManager);
        binding.timersSell.setAdapter(timeIntervalAdapter);
        binding.timersSell.addItemDecoration(dividerItemDecoration);

        binding.sbBattery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double value = round(i/100.0, 2);
                String sValue = String.format("%.2f", value);

                binding.etBatteryLevel.setText(sValue);
                getViewModel().setBatteryLevel(sValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void onRemoveTimeIntervalSelected(TimeInterval timeInterval) {
        getViewModel().removeTimerInterval(timeInterval);
    }

    private void onTimeIntervalSelected(TimeInterval timeInterval) {
        TimersActivity.launchForResult(getActivity(),REQUEST_TIMERS_EDIT, TIMER, timeInterval);
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
    }

    private void initNeighbours(Event event) {

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration_ = new CustomItemDecoration(dividerDrawable); //FIXME item decoration
        LinearLayoutManager layoutManagerConsumer = new LinearLayoutManager(getContext());
        List<NeighbourHeader> neighbourHeaders = getConsumers();
        NeighbourAdapter adapter = new NeighbourAdapter(getContext(), neighbourHeaders);
        getBinding().consumers.setLayoutManager(layoutManagerConsumer);
        getBinding().consumers.setAdapter(adapter);
        getBinding().consumers.addItemDecoration(dividerItemDecoration_);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public List<NeighbourHeader> getConsumers() {
        List<NeighbourHeader> neighbourHeaders = new ArrayList<>();
        List<Neighbour> neighbours = new ArrayList<>();
        neighbours.add(new Neighbour("0","Selecionar todos", true, false));
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
