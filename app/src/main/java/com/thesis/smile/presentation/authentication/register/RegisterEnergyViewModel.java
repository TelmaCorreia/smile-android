package com.thesis.smile.presentation.authentication.register;

import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.text.TextUtils;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.api.InvalidCredentialsException;
import com.thesis.smile.data.remote.exceptions.http.UnauthorizedException;
import com.thesis.smile.data.remote.models.ConfigsRemote;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.domain.managers.UtilsManager;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RegisterEnergyViewModel extends BaseViewModel {

    private AccountManager accountManager;
    private UtilsManager utilsManager;

    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> powers = new ArrayList<>();
    private ArrayList<String> tariffs = new ArrayList<>();
    private ArrayList<String> cycles = new ArrayList<>();

    private ObservableInt powerSelectedItem = new ObservableInt();


    private String category = "";
    private String power = "";
    private String tariff = "";
    private String cycle = "";


    private PublishRelay<Event> registerObservable = PublishRelay.create();

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
        notifyPropertyChanged(BR.registerEnabled);
    }

    @Bindable
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
        notifyPropertyChanged(BR.power);
        notifyPropertyChanged(BR.registerEnabled);
    }

    @Bindable
    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
        notifyPropertyChanged(BR.tariff);
        notifyPropertyChanged(BR.registerEnabled);
    }

    @Bindable
    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
        notifyPropertyChanged(BR.cycle);
        notifyPropertyChanged(BR.registerEnabled);
    }

    @Bindable
    public boolean isRegisterEnabled() {
        return !(category.isEmpty() || power.isEmpty() || tariff.isEmpty() || cycle.isEmpty());
    }

    @Bindable
    public ObservableInt getPowerSelectedItem() {
        return powerSelectedItem;
    }

    public void setPowerSelectedItem(ObservableInt powerSelectedItem) {
        this.powerSelectedItem = powerSelectedItem;
    }

    public void onRegisterClick() {
        register(category, power, tariff, cycle);

    }

    private void register(String category, String power, String tariff, String cycle) {
    }

    public ConfigsRemote getConfigs(){
        return utilsManager.getConfigs();
    }

    Observable<Event> observeRegister(){
        return registerObservable;
    }



}
