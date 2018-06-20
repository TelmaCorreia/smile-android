package com.thesis.smile.presentation.settings.energy_settings;

import android.databinding.Bindable;
import android.util.Log;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.managers.UtilsManager;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.EnergyParams;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class EnergySettingsViewModel extends BaseViewModel {

    private UserManager userManager;
    private UtilsManager utilsManager;

    private EnergyParams energyParams;
    private EnergyParams previousEnergyParams;
    private User user;
    private boolean manual = false;
    private boolean previousManual;

    private PublishRelay<NavigationEvent> openLearnMore = PublishRelay.create();

    @Inject
    public EnergySettingsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                   UiEvents uiEvents, UserManager userManager, UtilsManager utilsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.userManager = userManager;
        this.utilsManager = utilsManager;

        getUserFromSP();
    }

    @Bindable
    public String getCategory() {
        if(energyParams!=null){
            return energyParams.getCategory();
        }
        return "";
    }

    public void setCategory(String category) {
        this.energyParams.setCategory(category);
        notifyPropertyChanged(BR.category);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public String getPower() {
        if(energyParams!=null){
            return energyParams.getPower();
        }
        return "";
    }

    public void setPower(String power) {
        this.energyParams.setPower(power);
        notifyPropertyChanged(BR.power);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public String getTariff() {
        if(energyParams!=null){
            return energyParams.getTariff();
        }
        return "";    }

    public void setTariff(String tariff) {
        this.energyParams.setTariff(tariff);
        if (tariff.equals(getResourceProvider().getString(R.string.tariff_without_cycle))){
            setCycle(getResourceProvider().getString(R.string.no_cycle));
        }
        notifyPropertyChanged(BR.tariff);
        notifyPropertyChanged(BR.saveEnabled);
        notifyPropertyChanged(BR.cycleVisible);
    }

    @Bindable
    public String getCycle() {
        if(energyParams!=null){
            return energyParams.getCycle();
        }
        return "";
    }

    public void setCycle(String cycle) {
        this.energyParams.setCycle(cycle);
        notifyPropertyChanged(BR.cycle);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
        notifyPropertyChanged(BR.manual);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public boolean isSaveEnabled() {

        if(energyParams!=null) {
            return !(energyParams.getCategory().equals(previousEnergyParams.getCategory())
                    && energyParams.getPower().equals(previousEnergyParams.getPower())
                    && energyParams.getTariff().equals(previousEnergyParams.getTariff())
                    && energyParams.getCycle().equals(previousEnergyParams.getCycle())
                    && manual==previousManual);
        }
        return false;
    }

    @Bindable
    public boolean isCycleVisible() {
        if(energyParams!=null) {
            return !energyParams.getTariff().isEmpty() && !energyParams.getTariff().equals(getResourceProvider().getString(R.string.tariff_without_cycle));
        }
        return false;
    }

    public void onSaveClick() {
        user.setEnergyParams(energyParams);
        user.setManual(manual);
        if (isCycleVisible() && getCycle().equals(getResourceProvider().getString(R.string.no_cycle))){
            getUiEvents().showToast(getResourceProvider().getString(R.string.no_cycle_alert));
        }else{
            userManager.updateEnergyParams(user)
                    .compose(schedulersTransformSingleIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onUpdateComplete, this::onError);
        }

    }

    private void onUpdateComplete(User user) {
        userManager.saveUser(user);
        getUiEvents().showToast(getResourceProvider().getString(R.string.msg_update_sucess));
        this.user = user;
        this.previousEnergyParams = new EnergyParams(user.getEnergyParams().getCategory(), user.getEnergyParams().getPower(), user.getEnergyParams().getTariff(), user.getEnergyParams().getCycle());
        this.energyParams = user.getEnergyParams();
        this.manual = user.isManual();
        this.previousManual = user.isManual();
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public boolean isEquipmentEnabled() {
        return false; //could be useful int the future
    }

    Observable<NavigationEvent> openLearMoreObservable(){
        return openLearnMore;
    }

    public void onLearnMoreClick(){
        openLearnMore.accept(new NavigationEvent());
    }

    public Configs getConfigs() {
        return utilsManager.getConfigs();
    }


    public void getUserFromSP() {
        this.user = userManager.getCurrentUser();
        this.energyParams = user.getEnergyParams();
        this.manual = user.isManual();
        this.previousManual = user.isManual();
        this.previousEnergyParams = new EnergyParams(energyParams.getCategory(), energyParams.getPower(), energyParams.getTariff(), energyParams.getCycle());
        notifyChange();
    }

}
