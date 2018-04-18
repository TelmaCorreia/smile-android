package com.thesis.smile.presentation.main.transactions.sell;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentSellBinding;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.base.adapters.DividerItemDecoration;
import com.thesis.smile.presentation.main.transactions.expandable_list.NeighbourAdapter;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;

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

        List<NeighbourHeader> neighbourHeaders = new ArrayList<>();
        List<Neighbour> neighbours = new ArrayList<>();
        neighbours.add(new Neighbour("Filipe Magalhães", "Consumidor", "", true));
        neighbours.add(new Neighbour("Marta Magalhães", "Consumidor", "", true));
        neighbours.add(new Neighbour("Filipe Melo", "Consumidor", "", true));
        neighbours.add(new Neighbour("Miguel Silva", "Consumidor", "", false));

        NeighbourHeader neighbourHeader = new NeighbourHeader(getResources().getString(R.string.consumers_title), getResources().getString(R.string.consumers_description), neighbours);
        neighbourHeaders.add(neighbourHeader);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //instantiate your adapter with the list of genres
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        CustomItemDecoration dividerItemDecoration = new CustomItemDecoration(dividerDrawable);
        NeighbourAdapter adapter = new NeighbourAdapter(getContext(), neighbourHeaders);
        binding.consumers.setLayoutManager(layoutManager);
        binding.consumers.setAdapter(adapter);
        binding.consumers.addItemDecoration(dividerItemDecoration);


    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

    }

   /* private ArrayList<ParentObject> generateConsumers() {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        ArrayList<Object> childList = new ArrayList<>();
        childList.add(new Neighbour("Daniel Garigali", "consumidor", "", true));
        childList.add(new Neighbour("Luisa Barros", "consumidor", "", true));
        childList.add(new Neighbour("Dino Vasconcelos", "consumidor", "", false));
        NeighbourHeader neighbourHeader = new NeighbourHeader(getResources().getString(R.string.consumers_title), getResources().getString(R.string.consumers_description));
        neighbourHeader.setChildObjectList(childList);
        parentObjects.add(neighbourHeader);
        return parentObjects;
    }*/


}
