package com.thesis.smile.presentation.main.home;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.databinding.ActivityHomeDetailsBinding;
import com.thesis.smile.presentation.base.BaseActivity;
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
        initToolbar(binding.actionBar.toolbar, true);
        getViewModel().setType(type);

        TransactionsAdapter adapter = new TransactionsAdapter(getViewModel().getTransactions());
        binding.transactionsList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.transactionsList.setAdapter(adapter);

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
