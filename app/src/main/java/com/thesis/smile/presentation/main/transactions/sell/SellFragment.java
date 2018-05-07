package com.thesis.smile.presentation.main.transactions.sell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.views.CustomDialog;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SellFragment extends BaseFragment<FragmentSellBinding, SellViewModel> {

    static final int REQUEST_TIMERS = 1;
    static final int REQUEST_TIMERS_EDIT = 3;
    private static final String TIMER = "timer";

    private CustomDialog dialogAlert;
    private NeighbourAdapter neighbourAdapter;
    private TimeIntervalAdapter timeIntervalAdapter;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        timeIntervalAdapter = new TimeIntervalAdapter(getViewModel().getTimeIntervals(), this::onTimeIntervalSelected, this::onRemoveTimeIntervalSelected,  this::onTimeIntervalStateChanged);
        binding.timersSell.setLayoutManager(layoutManager);
        binding.timersSell.setAdapter(timeIntervalAdapter);

        binding.sbBattery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double value = round(i/100.0, 2);
                String sValue = String.format("%.2f", value);

                binding.etBatteryLevel.setText(sValue);
               // getViewModel().setBatteryLevel(sValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

        getViewModel().observeNeighboursState()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {if (neighbourAdapter!=null)neighbourAdapter.notifyDataSetChanged();});

        getViewModel().observeTimeIntervalState()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {timeIntervalAdapter.notifyDataSetChanged();});

        getViewModel().observeRadio()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::updateRadio);

        getViewModel().observeSlider()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::updateSlider);

        getViewModel().observeAlertDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::createDialogs);
    }

    //This is very bad :(
    private void createDialogs(OpenDialogEvent event) {
        if(getViewModel().getSellSettings().isOn()){
            int alarmsSize = getViewModel().getTimeIntervals().size();
            boolean allTimeIntervalsOff = areAllTimeIntervalsOff();
            int neighboursSize = getViewModel().getNeighbours().size();
            boolean allNeighboursOff = areAllNeighboursOff();
            double batterySaved = getViewModel().getSellSettings().getBatteryLevel();

            String description=getString(R.string.settings_alert_description) + "\n";

            if (neighboursSize==0 || allNeighboursOff){
                description += " - Não consegue vender energia a nenhum vizinho;\n";
            }else{
                description += " - Pode vender energia aos vizinhos especificados;\n";
            }
            if (alarmsSize==0 || allTimeIntervalsOff){
                description += " - Pode vender energia a qualquer hora;\n";
            }else{
                description += " - Pode vender energia nos períodos especificados;\n";
            }
            if (batterySaved==0){
                description += " - Pode vender TODA a energia que estiver armazenada na sua bateria;\n";
            }else{
                //FIXME: if battery capacity > 3 this is WRONG
                description += " - Pode vender " + round(100-(batterySaved*100/3),1) + "% da sua bateria";
            }

            showDialog(description);
            if(event instanceof OpenDialogEvent){
                dialogAlert.show();
            }else{
                dialogAlert.dismiss();
            }
        }else{
            getViewModel().save();
        }

    }

    private boolean areAllNeighboursOff() {
        if (getViewModel().isAllNeighboursSelected()){
            return false;
        }else{
            for (Neighbour n: getViewModel().getNeighbours()){
                if(!n.isBlocked()) return false;
            }
        }
        return true;
    }

    private boolean areAllTimeIntervalsOff() {
        for (TimeInterval t: getViewModel().getTimeIntervals()){
            if(t.isActivated()) return false;
        }
        return true;
    }

    private void showDialog(String description){
        dialogAlert = new CustomDialog(getActivity());
        dialogAlert.setTitle(R.string.settings_alert_tilte);
        dialogAlert.setMessage(description);
        dialogAlert.setSecondMessage(R.string.settings_alert_info);
        dialogAlert.setOkButtonText(R.string.button_continue);
        dialogAlert.setCloseButtonText(R.string.button_back);
        dialogAlert.setDismissible(true);
        dialogAlert.setOnOkClickListener(() -> {getViewModel().save(); dialogAlert.dismiss();});
        dialogAlert.setOnCloseClickListener(() ->{dialogAlert.dismiss();});
    }

    private void updateSlider(Event event) {
        int value = (int) round(getViewModel().getSellSettings().getBatteryLevel()*100,0);
        getBinding().sbBattery.setProgress(value);
    }

    private void updateRadio(Event event) {
        if (getViewModel().getSellSettings().isSpecificPrice()){
            getBinding().rbConcretePrice.setChecked(true);
            getBinding().rbPricePlus.setChecked(false);

        }else {
            getBinding().rbPricePlus.setChecked(true);
            getBinding().rbConcretePrice.setChecked(false);
        }
    }

    private void initNeighbours(Event event) {

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration_ = new CustomItemDecoration(dividerDrawable); //FIXME item decoration
        LinearLayoutManager layoutManagerConsumer = new LinearLayoutManager(getContext());
        List<NeighbourHeader> neighbourHeaders = getConsumers();
        neighbourAdapter = new NeighbourAdapter(getContext(), neighbourHeaders, this::onSwitchListener);
        getBinding().consumers.setLayoutManager(layoutManagerConsumer);
        getBinding().consumers.setAdapter(neighbourAdapter);
        getBinding().consumers.addItemDecoration(dividerItemDecoration_);
    }

    private void onSwitchListener(Neighbour neighbour) {
        if (neighbour.isSelectAll()){
            getViewModel().setAllNeighboursSelected(!getViewModel().isAllNeighboursSelected());
        }else{
            boolean blocked = !neighbour.isBlocked();
            neighbour.setBlocked(blocked);
            getViewModel().addNeighbourToUpdate(neighbour);
        }
    }

    public List<NeighbourHeader> getConsumers() {
        List<NeighbourHeader> neighbourHeaders = new ArrayList<>();
        List<Neighbour> neighbours = new ArrayList<>();
        neighbours.addAll(getViewModel().getNeighbours());
        NeighbourHeader neighbourHeader = new NeighbourHeader(getResources().getString(R.string.consumers_title), getResources().getString(R.string.consumers_description), neighbours);
        neighbourHeaders.add(neighbourHeader);
        return neighbourHeaders;
    }


    /**
     * TIMERS
     * **/

    private void onRemoveTimeIntervalSelected(TimeInterval timeInterval) {
        getViewModel().removeTimerInterval(timeInterval);
    }

    private void onTimeIntervalSelected(TimeInterval timeInterval) {
        TimersActivity.launchForResult(getActivity(),REQUEST_TIMERS_EDIT, TIMER, timeInterval);
    }

    private void onTimeIntervalStateChanged(TimeInterval timeInterval) {
        getViewModel().addTimerIntervalToUpdate(timeInterval);
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
