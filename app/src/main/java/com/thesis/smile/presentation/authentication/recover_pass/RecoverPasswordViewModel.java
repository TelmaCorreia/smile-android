package com.thesis.smile.presentation.authentication.recover_pass;

import android.app.Dialog;
import android.databinding.Bindable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;
import com.thesis.smile.BR;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RecoverPasswordViewModel extends BaseViewModel {

    private AccountManager accountManager;
    private String email = "";
    private String password = "";
    private String confirmPassword = "";
    private PublishRelay<DialogEvent> pinDialog = PublishRelay.create();


    @Inject
    public RecoverPasswordViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, AccountManager accountManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.accountManager = accountManager;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
        notifyPropertyChanged(BR.recoverEnabled);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
        notifyPropertyChanged(BR.recoverEnabled);
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String password) {
        this.confirmPassword = password;
        notifyPropertyChanged(BR.confirmPassword);
        notifyPropertyChanged(BR.recoverEnabled);
    }

    @Bindable
    public boolean isRecoverEnabled() {
        return !(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) && isPasswordValid(password, confirmPassword);
    }


    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword) && Utils.isPasswordValid(password);
    }
    public void onRecoverPasswordClick() {
        Disposable disposable = accountManager.recoverPasswordStep1(email, password)
                .compose(loadingTransformCompletable())
                .compose(schedulersTransformCompletableIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onRecoverPasswordCompleted, this::onError);
        addDisposable(disposable);

    }

    private void onRecoverPasswordCompleted() {

    }

    Observable<DialogEvent> observePinDialog(){
        return pinDialog;
    }

    public void recoverPasswordStep2() {
    }
}
