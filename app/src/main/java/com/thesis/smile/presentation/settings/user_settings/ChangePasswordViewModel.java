package com.thesis.smile.presentation.settings.user_settings;

import android.databinding.Bindable;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ChangePasswordViewModel extends BaseToolbarViewModel {

    private AccountManager accountManager;

    private String oldPassword = "";
    private String password = "";
    private String confirmPassword = "";
    private PublishRelay<NavigationEvent> backToSettings = PublishRelay.create();


    @Inject
    public ChangePasswordViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, AccountManager accountManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.accountManager = accountManager;
    }


    @Bindable
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        notifyPropertyChanged(BR.oldPassword);
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
        return !(password.isEmpty() || confirmPassword.isEmpty()|| oldPassword.isEmpty());
    }


    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword) && Utils.isPasswordValid(password);
    }
    public void onRecoverPasswordClick() {
        Disposable disposable = accountManager.changePassword(oldPassword, password)
                .compose(loadingTransformCompletable())
                .compose(schedulersTransformCompletableIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onChangePasswordCompleted, this::onError);
        addDisposable(disposable);

    }

    Observable<NavigationEvent> observeBackToSettings(){
        return backToSettings;
    }

    private void onChangePasswordCompleted() {
        getUiEvents().showToast(getResourceProvider().getString(R.string.recover_password_completed));
        backToSettings.accept(new NavigationEvent());

    }
}