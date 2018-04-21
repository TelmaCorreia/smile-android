package com.thesis.smile.presentation.main.transactions.timer_list;

import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thesis.smile.databinding.ListItemTimerBinding;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.adapters.BindableAdapter;
import com.thesis.smile.presentation.base.adapters.BindableViewHolder;

public class TimeIntervalAdapter extends BindableAdapter<TimeInterval> {

    public interface OnItemClickListener {
        void onItemClick(TimeInterval timeInterval);
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(TimeInterval timeInterval);
    }

    private OnItemClickListener onItemClickListener;
    private OnRemoveClickListener onRemoveClickListener;


    public TimeIntervalAdapter(@Nullable ObservableList<TimeInterval> items,  OnItemClickListener onItemClickListener, OnRemoveClickListener onRemoveClickListener) {
        super(items);

        this.onItemClickListener = onItemClickListener;
        this.onRemoveClickListener = onRemoveClickListener;

    }

    @Override
    public BindableViewHolder<TimeInterval, ? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TimeIntervalAdapter.TimeIntervalViewHolder(ListItemTimerBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindableViewHolder<TimeInterval, ? extends ViewDataBinding> holder, int position) {
        ((TimeIntervalAdapter.TimeIntervalViewHolder) holder).setTimeInterval(getItem(position));
    }

    class TimeIntervalViewHolder extends BindableViewHolder<TimeInterval, ListItemTimerBinding> {

        public TimeIntervalViewHolder(ListItemTimerBinding binding) {
            super(binding);
        }

        public void setTimeInterval(TimeInterval timeInterval) {
            binding.setViewModel(new TimeIntervalItemViewModel(timeInterval, onItemClickListener, onRemoveClickListener));
        }
    }
}
