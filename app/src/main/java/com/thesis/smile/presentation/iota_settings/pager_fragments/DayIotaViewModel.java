package com.thesis.smile.presentation.iota_settings.pager_fragments;

import android.databinding.Bindable;
import android.view.View;

import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.Totals;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class DayIotaViewModel extends BaseViewModel {

    private UserManager userManager;
    private TransactionsManager transactionsManager;
    private Totals totals;

    @Inject
    public DayIotaViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                            UiEvents uiEvents, UserManager userManager, TransactionsManager transactionsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.userManager = userManager;
        this.transactionsManager = transactionsManager;

        transactionsManager.getDailyTotals()
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onTotalsReceived, this::onError);
    }

    private void onTotalsReceived(Totals totals) {
        this.totals = totals;
        notifyChange();
    }

    @Bindable
    public int getUserTypeProsumer(){
        return userManager.getCurrentUser().getType().equals(getResourceProvider().getString(R.string.consumer))? View.GONE : View.VISIBLE;

    }

    @Bindable
    public String getIncome() {
       if (totals!=null){
            return String.format("%.2f", totals.getTotalSold()) + getResourceProvider().getString(R.string.coin);
        }

        return getResourceProvider().getString(R.string.no_data_placeholder);
    }

    @Bindable
    public String getOutcome() {
        if (totals!=null){
            return String.format("%.2f", totals.getTotalBought()) + getResourceProvider().getString(R.string.coin);
        }

        return getResourceProvider().getString(R.string.no_data_placeholder);
    }


}
