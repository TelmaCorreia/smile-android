package com.thesis.smile.presentation.main.transactions.buy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;

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
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.views.CustomDialog;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class BuyFragment extends BaseFragment<FragmentBuyBinding, BuyViewModel> {

    static final int REQUEST_TIMERS = 2;
    static final int REQUEST_TIMERS_EDIT = 4;
    private static final String TIMER = "timer";

    private CustomDialog dialogAlert;
    private NeighbourAdapter neighbourAdapter;
    private TimeIntervalAdapter timeIntervalAdapter;

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
        setupUI(binding.parent, this.getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        timeIntervalAdapter = new TimeIntervalAdapter(getViewModel().getTimeIntervals(),this::onTimeIntervalSelected,this::onRemoveTimeIntervalSelected, this::onTimeIntervalStateChanged);
        binding.timersBuy.setLayoutManager(layoutManager);
        binding.timersBuy.setAdapter(timeIntervalAdapter);

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

        getViewModel().observeAlertDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::createDialogs);

    }

    //This is very bad :(
    private void createDialogs(OpenDialogEvent event) {
        if(getViewModel().getBuySettings().isOn()){
            int alarmsSize = getViewModel().getTimeIntervals().size();
            boolean allTimeIntervalsOff = areAllTimeIntervalsOff();
            int neighboursSize = getViewModel().getNeighbours().size();
            boolean allNeighboursOff = areAllNeighboursOff();

            String description=getString(R.string.settings_alert_description) + "\n";

            if (neighboursSize==0 || allNeighboursOff){
                description += " - Não comprar energia a nenhum vizinho;\n";
            }else if (getViewModel().isAllNeighboursSelected()){
                description += " - Pode comprar energia ao todos os vizinhos;\n";
            }
            else{
                description += " - Pode comprar energia aos vizinhos especificados;\n";
            }
            if (alarmsSize==0 || allTimeIntervalsOff){
                description += " - Pode comprar energia a qualquer hora;\n";
            }else{
                description += " - Pode comprar energia nos períodos especificados;\n";
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
                if(n.isBlocked()) return false;
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
        dialogAlert.setOnCloseClickListener(() ->{getViewModel().setBuy(false);dialogAlert.dismiss();});
    }

    private void updateRadio(Event event) {
        if (getViewModel().getBuySettings().isEemPrice()){
            if(!getBinding().rbEemPrice.isChecked()) getBinding().rbEemPrice.setChecked(true);
        }else {
            if(!getBinding().rbEemPlusPrice.isChecked())getBinding().rbEemPlusPrice.setChecked(true);
        }
    }

    private void initNeighbours(Event event) {
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration_ = new CustomItemDecoration(dividerDrawable); //FIXME item decoration
        List<NeighbourHeader> neighbourHeaders = getSuppliers();
        LinearLayoutManager layoutManagerSup = new LinearLayoutManager(getContext());
        neighbourAdapter = new NeighbourAdapter(getContext(), neighbourHeaders, this::onSwitchListener);
        getBinding().suppliers.setLayoutManager(layoutManagerSup);
        getBinding().suppliers.setAdapter(neighbourAdapter);
        getBinding().suppliers.addItemDecoration(dividerItemDecoration_);

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

    public List<NeighbourHeader> getSuppliers() {
        List<NeighbourHeader> neighbourHeaders = new ArrayList<>();
        List<Neighbour> neighbours = new ArrayList<>();
        neighbours.addAll(getViewModel().getNeighbours());
        NeighbourHeader neighbourHeader = new NeighbourHeader(getResources().getString(R.string.suppliers_title), getResources().getString(R.string.suppliers_description), neighbours);
        neighbourHeaders.add(neighbourHeader);
        neighbourHeaders.add(new NeighbourHeader("", "", new ArrayList<>()));
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
}
