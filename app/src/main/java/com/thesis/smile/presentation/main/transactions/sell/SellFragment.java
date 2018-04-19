package com.thesis.smile.presentation.main.transactions.sell;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SeekBar;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentSellBinding;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thesis.smile.presentation.authentication.register.RegisterUserActivity;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.base.adapters.DividerItemDecoration;
import com.thesis.smile.presentation.main.transactions.expandable_list.NeighbourAdapter;
import com.thesis.smile.presentation.main.transactions.info_price.InfoPriceActivity;
import com.thesis.smile.presentation.main.transactions.timers.TimersActivity;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SellFragment extends BaseFragment<FragmentSellBinding, SellViewModel> {

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


        List<NeighbourHeader> neighbourHeaders = getConsumers();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //instantiate your adapter with the list of genres
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        //FIXME item decoration
        CustomItemDecoration dividerItemDecoration = new CustomItemDecoration(dividerDrawable);
        NeighbourAdapter adapter = new NeighbourAdapter(getContext(), neighbourHeaders);
        binding.consumers.setLayoutManager(layoutManager);
        binding.consumers.setAdapter(adapter);
        binding.consumers.addItemDecoration(dividerItemDecoration);

        binding.sbBattery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double value = round(i/100.0, 2);
                String sValue = String.valueOf(value);

                binding.etBatteryLevel.setText(sValue);

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
                    TimersActivity.launch(getContext());
                });

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
        neighbours.add(new Neighbour("Selecionar todos", true, false));
        neighbours.add(new Neighbour("Filipe Magalhães", "Consumidor", "", true));
        neighbours.add(new Neighbour("Marta Magalhães", "Consumidor", "", true));
        neighbours.add(new Neighbour("Filipe Melo", "Consumidor", "", true));
        neighbours.add(new Neighbour("Miguel Silva", "Consumidor", "", false));

        NeighbourHeader neighbourHeader = new NeighbourHeader(getResources().getString(R.string.consumers_title), getResources().getString(R.string.consumers_description), neighbours);
        neighbourHeaders.add(neighbourHeader);

        return neighbourHeaders;
    }
}
