package com.thesis.smile.presentation.main.transactions.transaction_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityTransactionDetailsBinding;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;

public class TransactionDetailsActivity extends BaseToolbarActivity<ActivityTransactionDetailsBinding, TransactionDetailsViewModel>{

    private static final String TRANSACTION = "transaction";
    private Transaction transaction;

    public static void launch(Context context, Transaction transaction) {
        Intent intent = new Intent(context, TransactionDetailsActivity.class);
        intent.putExtra(TRANSACTION, transaction);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_transaction_details;
    }

    @Override
    protected Class<TransactionDetailsViewModel> viewModelClass() {
        return TransactionDetailsViewModel.class;
    }

    @Override
    protected void initViews(ActivityTransactionDetailsBinding binding) {
        initToolbar(binding.actionBar.toolbar, true,  getResources().getString(R.string.transaction_details_title));
        getViewModel().setTransaction(transaction);
    }

    @Override
    protected void initArguments(Bundle args){
        super.initArguments(args);
        transaction = args.getParcelable(TRANSACTION);

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();


    }

}
