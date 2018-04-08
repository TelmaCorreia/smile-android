package com.thesis.smile.presentation.authentication.register.energy;

import android.databinding.Bindable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.domain.managers.UtilsManager;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RegisterEquipmentViewModel extends BaseViewModel {

    private AccountManager accountManager;
    private UtilsManager utilsManager;
    private RegisterRequest request;

    private boolean share;
    private String userType;

    private PublishRelay<Event> registerObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openGeneralInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openCycleInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> startMainObservable = PublishRelay.create();
    private PublishRelay<DialogEvent> shareDialogObservable = PublishRelay.create();

    @Inject
    public RegisterEquipmentViewModel(ResourceProvider resourceProvider,
                                      SchedulerProvider schedulerProvider, UiEvents uiEvents,
                                      AccountManager accountManager, UtilsManager utilsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.accountManager = accountManager;
        this.utilsManager = utilsManager;
        userType=getResourceProvider().getString(R.string.consumer);
    }


    @Bindable
    public boolean isRegisterEnabled() {
        return true; //could be useful int the future
    }

    @Bindable
    public boolean isEquipmentEnabled() {
        return false; //could be useful int the future
    }

    public void onRegisterClick() {
        shareDialogObservable.accept(new OpenDialogEvent());
        registerObservable.accept(new Event());
    }

    public void onGenerlaInfoClick() {
        openGeneralInfoObservable.accept(new NavigationEvent());
    }

    public void onCycleInfoClick() {
        openCycleInfoObservable.accept(new NavigationEvent());
    }

    public void setRequest(RegisterRequest request){
        this.request = request;
        register(request);
    }

    public void register(RegisterRequest request) {
        if (userType.isEmpty()){
            getUiEvents().showToast(getResourceProvider().getString(R.string.userTypeAlert));
        }else{
            request.setType(userType);
            request.setVisible(share);
            accountManager.register(request)
                    .compose(loadingTransformCompletable())
                    .compose(schedulersTransformCompletableIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onRegisterComplete, this::onError);
        }

    }

    private void onRegisterComplete() {
        startMainObservable.accept(new NavigationEvent());
    }

    public void setRegisterRequest(RegisterRequest registerRequest){
        this.request = registerRequest;
    }

    public Configs getConfigs() {

        return utilsManager.getConfigs();
    }
    Observable<Event> observeRegister(){
        return registerObservable;
    }

    Observable<NavigationEvent> observeStartMain(){
        return startMainObservable;
    }

    Observable<NavigationEvent> observeOpenGeneralInfo(){
        return openGeneralInfoObservable;
    }

    Observable<NavigationEvent> observeOpenCycleInfo(){
        return openCycleInfoObservable;
    }

    Observable<DialogEvent> observeShareDialog(){
        return shareDialogObservable;
    }


    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
