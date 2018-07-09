package com.thesis.smile.presentation.authentication.register.energy;

import android.databinding.Bindable;

import com.google.gson.Gson;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.EnergyParamsRemote;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.domain.managers.UtilsManager;
import com.thesis.smile.domain.mapper.EnergyParamsMapper;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.EnergyParams;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RegisterEnergyViewModel extends BaseViewModel {

    private AccountManager accountManager;
    private UtilsManager utilsManager;

    private String category = "";
    private String power = "";
    private String tariff = "";
    private String cycle = "";

    private RegisterRequest user = new RegisterRequest();

    private PublishRelay<Event> nextObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openGeneralInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openCycleInfoObservable = PublishRelay.create();


    @Inject
    public RegisterEnergyViewModel(ResourceProvider resourceProvider,
                                   SchedulerProvider schedulerProvider, UiEvents uiEvents,
                                   AccountManager accountManager, UtilsManager utilsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.accountManager = accountManager;
        this.utilsManager = utilsManager;

    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
        notifyPropertyChanged(BR.power);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
        if (tariff.equals(getResourceProvider().getString(R.string.tariff_without_cycle))){
            setCycle(getResourceProvider().getString(R.string.no_cycle));
        }
        notifyPropertyChanged(BR.tariff);
        notifyPropertyChanged(BR.nextEnabled);
        notifyPropertyChanged(BR.cycleVisible);
    }

    @Bindable
    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
        notifyPropertyChanged(BR.cycle);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public boolean isNextEnabled() {
        boolean condition = !(category.isEmpty() || power.isEmpty() || tariff.isEmpty());
        if (!tariff.equals(getResourceProvider().getString(R.string.tariff_without_cycle))){
            condition = condition && !cycle.isEmpty() && !cycle.equals(getResourceProvider().getString(R.string.no_cycle));
        }else{
            condition = condition && cycle.equals(getResourceProvider().getString(R.string.no_cycle));
        }
        return condition;
    }

    @Bindable
    public boolean isCycleVisible() {
        return !tariff.isEmpty() && !tariff.equals(getResourceProvider().getString(R.string.tariff_without_cycle));

    }

    public void onNextClick() {
        Configs configs = getConfigs();
        if (configs.getCategories().containsValue(category) && configs.getPower().containsValue(power)
                && configs.getTariff().containsValue(tariff)
                && (configs.getCycle().containsValue(cycle) || cycle.equals(getResourceProvider().getString(R.string.no_cycle)) )){
            user.setEnergyParams(EnergyParamsMapper.INSTANCE.domainToRemote(new EnergyParams(category, power, tariff, cycle)));
            nextObservable.accept(new Event());
        }
    }

    public void onGeneralInfoClick() {
        openGeneralInfoObservable.accept(new NavigationEvent());
    }

    public void onCycleInfoClick() {
        openCycleInfoObservable.accept(new NavigationEvent());
    }

    Observable<Event> observeNext(){
        return nextObservable;
    }

    Observable<NavigationEvent> observeOpenGeneralInfo(){
        return openGeneralInfoObservable;
    }

    Observable<NavigationEvent> observeOpenCycleInfo(){
        return openCycleInfoObservable;
    }


    public Configs getConfigs() {
        return utilsManager.getConfigs();
    }

    public String getRegisterRequest(RegisterRequest request) {
        request.setEnergyParams(user.getEnergyParams());
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }

}
