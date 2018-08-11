package com.thesis.smile.presentation.main.home;

import android.databinding.Bindable;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.api.NoContentException;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalTime;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomeViewModel extends BaseViewModel {

    private CurrentEnergy currentEnergy;
    private PublishRelay<Event> dataReceived = PublishRelay.create();
    private boolean isLoading = false;
    private TransactionsManager transactionsManager;
    private UserManager userManager;

    @Inject
    public HomeViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsManager transactionsManager, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.transactionsManager = transactionsManager;
        this.userManager = userManager;

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Home")
                .putContentType("Section Home")
                .putContentId("home")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail())
                .putCustomAttribute("hour", LocalTime.now().getHour()));

        getCurrentEnergyFromServer();
    }

    public CurrentEnergy getCurrentEnergy()
    {
        return this.currentEnergy;
    }
    @Bindable
    public String getProduction() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getProduction()) + " " + getResourceProvider().getString(R.string.watt) ;
        }
        return getResourceProvider().getString(R.string.no_data_placeholder);
    }

    @Bindable
    public String getConsumption() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getConsumption()) + " " + getResourceProvider().getString(R.string.watt);
        }
        return getResourceProvider().getString(R.string.no_data_placeholder);
    }

    @Bindable
    public int getUserTypeProsumer(){
        if(userManager.getCurrentUser()!=null && userManager.getCurrentUser().getType() !=null ){
            return userManager.getCurrentUser().getType().equals(getResourceProvider().getString(R.string.consumer))? View.GONE : View.VISIBLE;
        }
        return View.VISIBLE;

    }

    @Bindable
    public int getEnergySoldVisible(){
        if(currentEnergy !=  null){
            return currentEnergy.getTotalSold() > 0 ? View.VISIBLE : View.GONE;
        }
        return View.GONE;
    }

    @Bindable
    public boolean isProgress()
    {
        return this.isLoading;
    }

    Observable<Event> observeData(){
        return dataReceived;
    }


    public void getCurrentEnergyFromServer() {

        transactionsManager.getHomeData()
                .compose(schedulersTransformSingleIo())
                .repeatWhen(completed -> completed.delay(1, TimeUnit.MINUTES))
                .subscribe(this::onReceiveHomeData, this::onError);

    }

    private void onReceiveHomeData(CurrentEnergy currentEnergy) {
        setLoading(false);
        this.currentEnergy = currentEnergy;
        notifyChange();
        this.dataReceived.accept(new Event());
    }

    @Override
    public void onError(Throwable e){
        if(!(e instanceof NoContentException)) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.no_data_msg));
        }

    }

    public void setLoading(boolean paramBoolean)
    {
        this.isLoading = paramBoolean;
        notifyPropertyChanged(BR.progress);
    }


}
