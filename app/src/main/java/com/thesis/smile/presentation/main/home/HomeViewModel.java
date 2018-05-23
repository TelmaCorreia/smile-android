package com.thesis.smile.presentation.main.home;

import android.databinding.Bindable;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomeViewModel extends BaseViewModel {

    public PublishRelay<NavigationEvent> openHomeBoughtDetails = PublishRelay.create();
    public PublishRelay<NavigationEvent> openHomeSoldDetails = PublishRelay.create();

    private CurrentEnergy currentEnergy;
    private TransactionsManager transactionsManager;
    private UserManager userManager;

    @Inject
    public HomeViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsManager transactionsManager, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.transactionsManager = transactionsManager;
        this.userManager = userManager;

        getCurrentEnergyFromServer();
    }

    @Bindable
    public String getProduction() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getProduction());
        }
        return null;
    }

    @Bindable
    public String getConsumption() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getConsumption());
        }
        return null;
    }

    @Bindable
    public String getBatteryLevel() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getBatteryLevel());
        }
        return null;
    }

    @Bindable
    public String getBatteryKWH() {
        if(currentEnergy != null){
            return String.valueOf(currentEnergy.getBatteryKWH());
        }
        return null;
    }

    @Bindable
    public String getTotalBought() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getTotalBought());
        }
        return null;
    }

    @Bindable
    public String getTotalSold() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getTotalSold());
        }
        return null;
    }

    @Bindable
    public String getTotalSolarEnergy() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getTotalSolarEnergy());
        }
        return null;
    }

    @Bindable
    public int getEnergyBoughtVisible(){
        if(currentEnergy !=  null){
            return currentEnergy.getTotalBought() > 0 ? View.VISIBLE : View.GONE;
        }
        return View.GONE;
    }

    @Bindable
    public int getEnergyBoughtInvisible(){
        if(getEnergyBoughtVisible()== View.GONE){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    @Bindable
    public int getEnergySoldVisible(){
        if(currentEnergy !=  null){
            return currentEnergy.getTotalSold() > 0 ? View.VISIBLE : View.GONE;
        }
        return View.GONE;
    }

    @Bindable
    public int getUserTypeProsumer(){
        return userManager.getCurrentUser().getType().equals(getResourceProvider().getString(R.string.consumer))? View.GONE : View.VISIBLE;

    }

    @Bindable
    public int getEnergySoldInvisible(){
        if(getEnergySoldVisible()== View.GONE && getUserTypeProsumer()!=View.GONE){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public void onEnergySoldDetailsClick(){
        openHomeSoldDetails.accept(new NavigationEvent());
    }

    public void onEnergyBoughtDetailsClick(){
        openHomeBoughtDetails.accept(new NavigationEvent());
    }


    Observable<NavigationEvent> observeOpenHomeBoughtDetails(){
        return openHomeBoughtDetails;
    }

    Observable<NavigationEvent> observeOpenHomeSoldDetails(){
        return openHomeSoldDetails;
    }


    public void getCurrentEnergyFromServer() {

        transactionsManager.getCurrentEnergyData()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onReceiveCurrentEnergy, this::onError);

    }

    private void onReceiveCurrentEnergy(CurrentEnergy currentEnergy) {
        this.currentEnergy = currentEnergy;
        notifyChange();
    }
}
