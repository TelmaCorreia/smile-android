package com.thesis.smile.presentation.main.home.list;

import android.databinding.Bindable;

import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseViewModelInstance;

import org.threeten.bp.format.DateTimeFormatter;


public class TransactionItemViewModel extends BaseViewModelInstance{

    private Transaction transaction;

    public TransactionItemViewModel(Transaction transaction) {
        this.transaction = transaction;
    }

    @Bindable
    public String getName() {
        if (transaction.getType().equals("compra")){
            return transaction.getFrom();
        }
        return transaction.getTo();

    }
    @Bindable
    public String getUrl() {
        return transaction.getUrl();
    }
    @Bindable
    public String getType(){
        if(transaction.getType().equals("compra")){
            return "Fornecedor";
        }
        return "Cliente";
    }
    @Bindable
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return transaction.getDate().format(formatter);
    }

    @Bindable
    public String getPriceKWH() {
        return String.format("%.2f",transaction.getPriceKWH()) + " €";
    }

    @Bindable
    public String getQuantity() {
        return String.format("%.2f",transaction.getQuantity()) + "kWh";
    }

    @Bindable
    public String getTotal() {
        return String.format("%.2f", transaction.getTotal()) + " €";
    }
}
