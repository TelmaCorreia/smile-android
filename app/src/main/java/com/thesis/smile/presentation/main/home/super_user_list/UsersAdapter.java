package com.thesis.smile.presentation.main.home.super_user_list;

import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thesis.smile.databinding.ListItemUserSelectionBinding;
import com.thesis.smile.domain.models.Users;
import com.thesis.smile.presentation.base.adapters.BindableAdapter;
import com.thesis.smile.presentation.base.adapters.BindableViewHolder;

public class UsersAdapter extends BindableAdapter<Users> {


    public interface onSelectClickListener {
        void onStateClick(Users users);
    }

    private onSelectClickListener onSelectClickListener;

    public UsersAdapter(@Nullable ObservableList<Users> items, onSelectClickListener onSelectClickListener) {
        super(items);

        this.onSelectClickListener = onSelectClickListener;

    }

    @Override
    public BindableViewHolder<Users, ? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new UsersViewHolder(ListItemUserSelectionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindableViewHolder<Users, ? extends ViewDataBinding> holder, int position) {
        ((UsersViewHolder) holder).setUsers(getItem(position));
    }

    class UsersViewHolder extends BindableViewHolder<Users, ListItemUserSelectionBinding> {

        public UsersViewHolder(ListItemUserSelectionBinding binding) {
            super(binding);
        }

        public void setUsers(Users users) {
            binding.setViewModel(new UsersItemViewModel(users, onSelectClickListener));
        }
    }
}
