package com.thesis.smile.presentation.main.home;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.databinding.ActivityHomeDetailsBinding;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.base.adapters.DividerItemDecoration;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.main.home.list.TransactionsAdapter;

public class HomeDetailsActivity extends BaseToolbarActivity<ActivityHomeDetailsBinding, HomeDetailsViewModel> {

    private static final String TYPE = "TYPE";
    private String type;
    public static void launch(Context context, String type) {
        Intent intent = new Intent(context, HomeDetailsActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_home_details;
    }

    @Override
    protected Class viewModelClass() {
        return HomeDetailsViewModel.class;
    }

    @Override
    protected void initViews(ActivityHomeDetailsBinding binding) {
        String title = type.equals(getResources().getString(R.string.details_bought_energy))?
                getResources().getString(R.string.title_energy_bought):
                getResources().getString(R.string.title_energy_sold);

        initToolbar(binding.actionBar.toolbar, true, title);
        getViewModel().setType(type);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        TransactionsAdapter adapter = new TransactionsAdapter(getViewModel().getTransactions());
        binding.transactionsList.setAdapter(adapter);
        binding.transactionsList.addItemDecoration(dividerItemDecoration);
    }
    @Override
    protected void registerObservables() {
        super.registerObservables();


    }

    @Override
    protected void initArguments(Bundle args) {
        super.initArguments(args);
        this.type = args.getString(TYPE);

    }

}
