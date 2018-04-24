package com.thesis.smile.presentation.main.transactions.transaction_details;

import android.databinding.Bindable;

import com.thesis.smile.R;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.format.DateTimeFormatter;

import javax.inject.Inject;

public class TransactionDetailsViewModel extends BaseToolbarViewModel {
    

    Transaction transaction;

    @Inject
    public TransactionDetailsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);
    }

    @Bindable
    public String getDate() {
        if (transaction!=null){
            return transaction.getDateString();
        }
        return "";
    }

    @Bindable
    public String getClient() {
        if (transaction!=null){
            return transaction.getName(); //fixme
        }
        return "";
    }

    @Bindable
    public String getSupplier() {
        if (transaction!=null){
            return transaction.getName(); //fixme
        }
        return "";
    }

    @Bindable
    public String getTotal() {
        if (transaction!=null){
            return String.format("%.2f",transaction.getTotal())  + " " + getResourceProvider().getString(R.string.coin);
        }
        return "";
    }

    @Bindable
    public String getQuantity() {
        if (transaction!=null){
            return String.valueOf(transaction.getQuantity()) + " " + getResourceProvider().getString(R.string.kWHour);
        }
        return "";
    }

    @Bindable
    public String getPriceKWH() {
        if (transaction!=null){
            return String.valueOf(transaction.getPriceKWH()) + " " + getResourceProvider().getString(R.string.coin);
        }
        return "";
    }


    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
