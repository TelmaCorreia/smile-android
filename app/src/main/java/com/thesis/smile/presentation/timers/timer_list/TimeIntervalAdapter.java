package com.thesis.smile.presentation.timers.timer_list;

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

    public interface OnStateChangeClickListener {
        void onStateClick(TimeInterval timeInterval);
    }

    private OnItemClickListener onItemClickListener;
    private OnRemoveClickListener onRemoveClickListener;
    private OnStateChangeClickListener onStateChangeClickListener;

    public TimeIntervalAdapter(@Nullable ObservableList<TimeInterval> items,  OnItemClickListener onItemClickListener, OnRemoveClickListener onRemoveClickListener, OnStateChangeClickListener onStateChangeClickListener) {
        super(items);

        this.onItemClickListener = onItemClickListener;
        this.onRemoveClickListener = onRemoveClickListener;
        this.onStateChangeClickListener = onStateChangeClickListener;

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
            binding.setViewModel(new TimeIntervalItemViewModel(timeInterval, onItemClickListener, onRemoveClickListener, onStateChangeClickListener));
        }
    }
}
