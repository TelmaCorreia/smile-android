package com.thesis.smile.presentation.authentication;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.api.InvalidCredentialsException;
import com.thesis.smile.data.remote.exceptions.http.UnauthorizedException;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.AppUpdatesEvents;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoginViewModel extends BaseViewModel {

    private AccountManager accountManager;

    private String email = "";
    private String password = "";

    private PublishRelay<Event> startLoginOnservable = PublishRelay.create();

    @Inject
    public LoginViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, AppUpdatesEvents appUpdatesEvents) {
        super(resourceProvider, schedulerProvider, uiEvents, appUpdatesEvents);

        this.accountManager = accountManager;

    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
        notifyPropertyChanged(BR.loginEnabled);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
        notifyPropertyChanged(BR.loginEnabled);
    }


    @Bindable
    public boolean isLoginEnabled() {
        return !(email.isEmpty() || password.isEmpty());
    }

    public void onLoginClick() {
        login(email, password);

    }

    private void login(String email, String password) {

        // Don't do anything if one of the fields is empty.
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            return;
        }

        // Make a simple email validation before submitting.
        if (!Utils.isEmailValid(email)) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_invalid_email));

            return;
        }

        accountManager.login(email, password)
                .compose(loadingTransformCompletable())
                .compose(schedulersTransformCompletableIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onLoginComplete, this::onError);
    }

    private void onLoginComplete(){
        startLoginOnservable.accept(new Event());
    }



    @Override
    protected void onError(Throwable e){
        if (e instanceof InvalidCredentialsException) {
            getUiEvents().showToast(e.getMessage());
        } else if(e instanceof UnauthorizedException){
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_invalid_login));
        } else {
            super.onError(e);
        }

        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }


    Observable<Event> observeStartLogin(){
        return startLoginOnservable;
    }




}
