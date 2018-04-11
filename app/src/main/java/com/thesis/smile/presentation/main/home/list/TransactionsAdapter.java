package com.thesis.smile.presentation.main.home.list;

import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thesis.smile.databinding.ListItemHomeDetailsBinding;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.adapters.BindableAdapter;
import com.thesis.smile.presentation.base.adapters.BindableViewHolder;


public class TransactionsAdapter extends BindableAdapter<Transaction> {

    public TransactionsAdapter(@Nullable ObservableList<Transaction> items) {
        super(items);
    }

    @Override
    public BindableViewHolder<Transaction, ? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TransactionViewHolder(ListItemHomeDetailsBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindableViewHolder<Transaction, ? extends ViewDataBinding> holder, int position) {
        ((TransactionViewHolder) holder).setTransaction(getItem(position));
    }

    class TransactionViewHolder extends BindableViewHolder<Transaction, ListItemHomeDetailsBinding> {

        public TransactionViewHolder(ListItemHomeDetailsBinding binding) {
            super(binding);
        }

        public void setTransaction(Transaction transaction) {
            binding.setViewModel(new TransactionItemViewModel(transaction));
        }
    }
}
