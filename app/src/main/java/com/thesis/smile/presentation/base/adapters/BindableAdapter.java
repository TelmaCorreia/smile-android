package com.thesis.smile.presentation.base.adapters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.Collection;

public abstract class BindableAdapter<T> extends RecyclerView.Adapter<BindableViewHolder<T, ? extends ViewDataBinding>> {

    private final WeakReferenceOnListChangedCallback<T> onListChangedCallback;

    private ObservableList<T> items;

    public BindableAdapter(@Nullable ObservableList<T> items) {
        onListChangedCallback = new WeakReferenceOnListChangedCallback<>(this);
        setItems(items);
    }

    public ObservableList<T> getItems() {
        return items;
    }

    public void setItems(@Nullable Collection<T> items) {
        if (this.items == items) {
            return;
        }

        if (this.items != null) {
            this.items.removeOnListChangedCallback(onListChangedCallback);
            notifyItemRangeRemoved(0, this.items.size());
        }

        if (items instanceof ObservableList) {
            this.items = (ObservableList<T>) items;
            notifyItemRangeInserted(0, this.items.size());
            this.items.addOnListChangedCallback(onListChangedCallback);
        } else if (items != null) {
            this.items = new ObservableArrayList<>();
            this.items.addOnListChangedCallback(onListChangedCallback);
            this.items.addAll(items);
        } else {
            this.items = null;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (items != null) {
            items.removeOnListChangedCallback(onListChangedCallback);
        }
    }

    @Override
    public void onBindViewHolder(BindableViewHolder<T, ? extends ViewDataBinding> holder, int position) {
        holder.bind(getItem(position));
    }

    @CallSuper
    @Override
    public void onViewRecycled(BindableViewHolder<T, ? extends ViewDataBinding> holder) {
        holder.unbind();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected T getItem(int position) {
        return items.get(position);
    }

    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {

        private final WeakReference<BindableAdapter<T>> adapterReference;

        WeakReferenceOnListChangedCallback(BindableAdapter<T> bindableAdapter) {
            this.adapterReference = new WeakReference<>(bindableAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }
    }

}