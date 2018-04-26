package com.thesis.smile.presentation.main.transactions.historical_transactions.list;

import android.databinding.Bindable;

import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseViewModelInstance;

public class TransactionItemViewModel extends BaseViewModelInstance {

    private Transaction transaction;
    private HistoricalTransactionAdapter.OnItemClickListener onItemClickListener;

    public TransactionItemViewModel(Transaction transaction, HistoricalTransactionAdapter.OnItemClickListener onItemClickListener) {
        this.transaction = transaction;
        this.onItemClickListener = onItemClickListener;

    }

    @Bindable
    public String getName() {
        if (getType().equals("compra")){
            return transaction.getFrom();
        }
        return transaction.getTo();
    }

    @Bindable
    public String getType() {
        return transaction.getType();
    }

    @Bindable
    public String getValue() {
        return String.format("%.2f",transaction.getTotal());
    }

    public void onTransactionClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(transaction);
        }
    }
}
