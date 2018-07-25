package com.thesis.smile.presentation.transaction_details;

import android.databinding.Bindable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

public class TransactionDetailsViewModel extends BaseToolbarViewModel {
    

    Transaction transaction;
    UserManager userManager;

    @Inject
    public TransactionDetailsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.userManager = userManager;
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Transactions:historical get transaction details")
                .putContentType("Section Transactions")
                .putContentId("transactions_historical_details")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail()));
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
            return transaction.getTo();
        }
        return "";
    }

    @Bindable
    public String getSupplier() {
        if (transaction!=null){
            return transaction.getFrom();
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
            return String.format("%.2f",transaction.getQuantity()) + " " + getResourceProvider().getString(R.string.kWHour);
        }
        return "";
    }

    @Bindable
    public String getPriceKWH() {
        if (transaction!=null){
            return String.format("%.2f",transaction.getPriceKWH()) + " " + getResourceProvider().getString(R.string.coin);
        }
        return "";
    }


    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
