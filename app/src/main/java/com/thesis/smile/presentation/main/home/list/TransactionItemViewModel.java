package com.thesis.smile.presentation.main.home.list;

import android.databinding.Bindable;

import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.base.BaseViewModelInstance;


import org.threeten.bp.format.DateTimeFormatter;

import io.reactivex.Observable;

public class TransactionItemViewModel extends BaseViewModelInstance{

    private Transaction transaction;

    public TransactionItemViewModel(Transaction transaction) {
        this.transaction = transaction;
    }

    @Bindable
    public String getName() {
        return transaction.getName();
    }
    @Bindable
    public String getUrl() {
        return transaction.getUrl();
    }

    @Bindable
    public String getDate() {
        return transaction.getDate().toString();
    }

    @Bindable
    public String getPriceKWH() {
        return String.valueOf(transaction.getPriceKWH());
    }

    @Bindable
    public String getQuantity() {
        return String.valueOf(transaction.getQuantity());
    }

    @Bindable
    public String getTotal() {
        return String.valueOf(transaction.getTotal());
    }
}
