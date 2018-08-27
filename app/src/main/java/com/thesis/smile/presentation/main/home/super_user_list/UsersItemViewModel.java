package com.thesis.smile.presentation.main.home.super_user_list;

import android.databinding.Bindable;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Users;
import com.thesis.smile.presentation.base.BaseViewModelInstance;

import java.util.List;

public class UsersItemViewModel extends BaseViewModelInstance{

    private Users users;
    private UsersAdapter.onSelectClickListener onSelectClickListener;

    public UsersItemViewModel(Users users, UsersAdapter.onSelectClickListener onSelectClickListener) {
        this.users = users;
        this.onSelectClickListener = onSelectClickListener;
    }

    @Bindable
    public int getColor() {
        return users.isOn()? R.color.colorGreen:R.color.colorRed;
    }

    @Bindable
    public String getInfo() {
        return users.getName();
    }

    @Bindable
    public String getEmail() {
        return users.getEmail();
    }

    @Bindable
    public String getTecInfo() {
        return users.getSmart_meter_id() + ": " +users.getToken();
    }

    public void onStateChangeClick() {
        if (onSelectClickListener != null) {
            onSelectClickListener.onStateClick(users);
        }
    }



}
