package com.thesis.smile.presentation.main.home.pager_fragments;

import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.api.NoContentException;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RenewableViewModel extends BaseViewModel {

    private CurrentEnergy currentEnergy;
    private PublishRelay<Event> dataReceived = PublishRelay.create();
    private boolean isLoading = false;
    private TransactionsManager transactionsManager;
    private UserManager userManager;

    @Inject
    public RenewableViewModel(ResourceProvider paramResourceProvider, SchedulerProvider paramSchedulerProvider, UiEvents paramUiEvents, UserManager paramUserManager, TransactionsManager paramTransactionsManager)
    {
        super(paramResourceProvider, paramSchedulerProvider, paramUiEvents);
        this.userManager = paramUserManager;
        this.transactionsManager = paramTransactionsManager;
        getCurrentEnergyFromServer();
    }

    private void onReceiveHomeData(CurrentEnergy paramCurrentEnergy) {
        setLoading(false);
        this.currentEnergy = paramCurrentEnergy;
        notifyChange();
        this.dataReceived.accept(new Event());
    }

    public CurrentEnergy getCurrentEnergy() {
        return this.currentEnergy;
    }

    public void getCurrentEnergyFromServer() {

        transactionsManager.getHomeData()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onReceiveHomeData, this::onError);

    }

    @Bindable
    public String getResultText()
    {
        if (this.currentEnergy != null)
        {
            double d = this.currentEnergy.getTotalSolarEnergy();
            if (d < 10.0D) {
                return getResourceProvider().getString(R.string.renewable_bad);
            }
            if (d < 25.0D) {
                return getResourceProvider().getString(R.string.renewable_avg);
            }
            if (d < 75.0D) {
                return getResourceProvider().getString(R.string.renewable_good);
            }
            return getResourceProvider().getString(R.string.renewable_excellent);
        }
        return null;
    }

    @Bindable
    public Drawable getSmileImage()
    {
        if (this.currentEnergy != null)
        {
            if (this.currentEnergy != null)
            {
                double d = this.currentEnergy.getTotalSolarEnergy();
                if (d < 10.0D) {
                    return getResourceProvider().getDrawable(R.drawable.smile_bad);
                }
                if (d < 25.0D) {
                    return getResourceProvider().getDrawable(R.drawable.smile_average);
                }
                if (d < 75.0D) {
                    return getResourceProvider().getDrawable(R.drawable.smile_good);
                }
                return getResourceProvider().getDrawable(R.drawable.smile_excellent);
            }
            return null;
        }
        return getResourceProvider().getDrawable(R.drawable.ic_broken_image);
    }
    @Bindable
    public String getTotalSolarEnergy()
    {
        if (this.currentEnergy != null)
        {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(String.format("%.2f", new Object[] { Double.valueOf(this.currentEnergy.getTotalSolarEnergy()) }));
            localStringBuilder.append(" ");
            localStringBuilder.append(getResourceProvider().getString(R.string.percentage));
            return localStringBuilder.toString();
        }
        return getResourceProvider().getString(R.string.no_data_placeholder1);
    }

    public float getValue()
    {
        if (this.currentEnergy != null) {
            return (float)this.currentEnergy.getTotalSolarEnergy();
        }
        return 0.0F;
    }

    @Bindable
    public boolean isProgress()
    {
        return this.isLoading;
    }

    public Observable<Event> observeData()
    {
        return this.dataReceived;
    }

    public void setLoading(boolean paramBoolean)
    {
        this.isLoading = paramBoolean;
        notifyPropertyChanged(BR.progress);
    }

    @Override
    public void onError(Throwable paramThrowable)
    {
        if (!(paramThrowable instanceof NoContentException)) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.no_data_msg));
        }
    }


}
