package com.thesis.smile.presentation.main.transactions.historical_transactions.list;

import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thesis.smile.databinding.ListItemTransactionBinding;
import com.thesis.smile.databinding.ListItemTransactionHeaderBinding;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.adapters.BindableAdapter;
import com.thesis.smile.presentation.base.adapters.BindableViewHolder;

public class HistoricalTransactionAdapter extends BindableAdapter<Transaction> {

    public interface OnItemClickListener {
        void onItemClick(Transaction transaction);
    }

    private OnItemClickListener onItemClickListener;

    public HistoricalTransactionAdapter(@Nullable ObservableList<Transaction> items, OnItemClickListener onItemClickListener) {
        super(items);

        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BindableViewHolder<Transaction, ? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new HistoricalTransactionAdapter.HistoricalTransactionViewHolder(ListItemTransactionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindableViewHolder<Transaction, ? extends ViewDataBinding> holder, int position) {
        ((HistoricalTransactionAdapter.HistoricalTransactionViewHolder) holder).setTransaction(getItem(position));
    }

    class HistoricalTransactionViewHolder extends BindableViewHolder<Transaction, ListItemTransactionBinding> {

        public HistoricalTransactionViewHolder(ListItemTransactionBinding binding) {
            super(binding);
        }

        public void setTransaction(Transaction timeInterval) {
            binding.setViewModel(new TransactionItemViewModel(timeInterval, onItemClickListener));
        }
    }

    class HeaderViewHolder extends BindableViewHolder<String, ListItemTransactionHeaderBinding> {

        public HeaderViewHolder(ListItemTransactionHeaderBinding binding) {
            super(binding);
        }

        public void setTitle(String title) {
            binding.tvHeader.setText(title);
        }
    }

}
