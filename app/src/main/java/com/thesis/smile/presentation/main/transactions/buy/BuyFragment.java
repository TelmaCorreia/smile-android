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
import com.thesis.smile.presentation.main.transactions.expandable_list.NeighbourAdapter;
import com.thesis.smile.presentation.main.transactions.info_price.InfoPriceActivity;
import com.thesis.smile.presentation.main.transactions.timer_list.TimeIntervalAdapter;
import com.thesis.smile.presentation.main.transactions.timers.TimersActivity;
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

        List<NeighbourHeader> neighbourHeaders = getSuppliers();
        LinearLayoutManager layoutManagerSup = new LinearLayoutManager(getContext());
        NeighbourAdapter adapter = new NeighbourAdapter(getContext(), neighbourHeaders);
        binding.suppliers.setLayoutManager(layoutManagerSup);
        binding.suppliers.setAdapter(adapter);
        binding.suppliers.addItemDecoration(dividerItemDecoration);

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
    }

    private void onTimeIntervalSelected(TimeInterval timeInterval) {
        TimersActivity.launchForResult(getActivity(),REQUEST_TIMERS_EDIT, TIMER, timeInterval);
    }

    public List<NeighbourHeader> getSuppliers() {
        List<NeighbourHeader> neighbourHeaders = new ArrayList<>();
        List<Neighbour> neighbours = new ArrayList<>();
        neighbours.add(new Neighbour("Selecionar todos", true, false));
        neighbours.add(new Neighbour("Filipe Magalhães", "Fornecedor", "", true));
        neighbours.add(new Neighbour("Marta Magalhães", "Fornecedor", "", true));
        neighbours.add(new Neighbour("Filipe Melo", "Fornecedor", "", true));
        neighbours.add(new Neighbour("Miguel Silva", "Fornecedor", "", false));

        NeighbourHeader neighbourHeader = new NeighbourHeader(getResources().getString(R.string.suppliers_title), getResources().getString(R.string.suppliers_description), neighbours);
        neighbourHeaders.add(neighbourHeader);

        return neighbourHeaders;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TIMERS && resultCode == Activity.RESULT_OK && data.getExtras() != null){
            if(data != null && data.getExtras() != null){
                TimeInterval timeInterval = data.getExtras().getParcelable(TIMER);
                getViewModel().addTimeInterval(timeInterval);
            }
        }else if (requestCode == REQUEST_TIMERS_EDIT && resultCode == Activity.RESULT_OK && data.getExtras() != null){
            TimeInterval timeInterval = data.getExtras().getParcelable(TIMER);
            getViewModel().addTimeInterval(timeInterval);
        }
    }
}
