package com.thesis.smile.presentation.main.home.pager_fragments;

import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.api.NoContentException;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalTime;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class BatteryViewModel extends BaseViewModel {

    private CurrentEnergy currentEnergy;
    private TransactionsManager transactionsManager;
    private UserManager userManager;

    @Inject
    public BatteryViewModel(ResourceProvider paramResourceProvider, SchedulerProvider paramSchedulerProvider, UiEvents paramUiEvents, UserManager paramUserManager, TransactionsManager paramTransactionsManager)
    {
        super(paramResourceProvider, paramSchedulerProvider, paramUiEvents);
        this.userManager = paramUserManager;
        this.transactionsManager = paramTransactionsManager;
        getCurrentEnergyFromServer();
    }

    private void onReceiveHomeData(CurrentEnergy paramCurrentEnergy)
    {
        this.currentEnergy = paramCurrentEnergy;
        notifyChange();
    }

    @Bindable
    public Drawable getBatteryImage()
    {
        if ((this.currentEnergy != null) && (this.currentEnergy != null))
        {
            double d = this.currentEnergy.getBatteryKWH();
            if (d == 0.0D) {
                return getResourceProvider().getDrawable(R.drawable.battery);
            }
            if (d < 20.0D) {
                return getResourceProvider().getDrawable(R.drawable.battery_one);
            }
            if (d < 40.0D) {
                return getResourceProvider().getDrawable(R.drawable.battery_two);
            }
            if (d < 60.0D) {
                return getResourceProvider().getDrawable(R.drawable.batery_three);
            }
            if (d < 80.0D) {
                return getResourceProvider().getDrawable(R.drawable.battery_four);
            }
            return getResourceProvider().getDrawable(R.drawable.battery_five);
        }
        return getResourceProvider().getDrawable(R.drawable.battery);
    }

    @Bindable
    public String getBatteryKWH()
    {
        if (this.currentEnergy != null)
        {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(String.format("%.2f", new Object[] { Double.valueOf(this.currentEnergy.getBatteryKWH()) }));
            localStringBuilder.append(" ");
            localStringBuilder.append(getResourceProvider().getString(R.string.kWHour));
            return localStringBuilder.toString();
        }
        return getResourceProvider().getString(R.string.no_data_placeholder1);
    }

    @Bindable
    public String getBatteryLevel()
    {
        if (this.currentEnergy != null)
        {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(String.format("%.2f", new Object[] { Double.valueOf(this.currentEnergy.getBatteryLevel()) }));
            localStringBuilder.append(" ");
            localStringBuilder.append(getResourceProvider().getString(R.string.percentage));
            return localStringBuilder.toString();
        }
        return getResourceProvider().getString(R.string.no_data_placeholder1);
    }

    public void getCurrentEnergyFromServer() {

        transactionsManager.getHomeData()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onReceiveHomeData, this::onError);

    }
    @Override
    public void onError(Throwable paramThrowable)
    {
        if (!(paramThrowable instanceof NoContentException)) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.no_data_msg));
        }
    }


}
