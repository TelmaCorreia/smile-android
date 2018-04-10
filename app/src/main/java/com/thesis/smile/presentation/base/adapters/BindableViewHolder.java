package com.thesis.smile.presentation.base.adapters;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;

import com.thesis.smile.BR;

public abstract class BindableViewHolder<T, ViewBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected final ViewBinding binding;

    protected T item;

    public BindableViewHolder(ViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @CallSuper
    public void bind(T item) {
        this.item = item;

        binding.setVariable(BR.item, item);
        binding.executePendingBindings();
    }

    public void unbind() {}
}