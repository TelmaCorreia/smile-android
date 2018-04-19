package com.thesis.smile.presentation.main.transactions.buy;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentBuyBinding;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.NeighbourHeader;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.main.transactions.expandable_list.NeighbourAdapter;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BuyFragment extends BaseFragment<FragmentBuyBinding, BuyViewModel> {

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

        List<NeighbourHeader> neighbourHeaders = getSuppliers();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //instantiate your adapter with the list of genres
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        //FIXME item decoration
        CustomItemDecoration dividerItemDecoration = new CustomItemDecoration(dividerDrawable);
        NeighbourAdapter adapter = new NeighbourAdapter(getContext(), neighbourHeaders);
        binding.suppliers.setLayoutManager(layoutManager);
        binding.suppliers.setAdapter(adapter);
        binding.suppliers.addItemDecoration(dividerItemDecoration);

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

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
}
