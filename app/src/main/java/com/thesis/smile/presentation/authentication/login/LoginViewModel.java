package com.thesis.smile.presentation.authentication.login;

import android.databinding.Bindable;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.api.InvalidCredentialsException;
import com.thesis.smile.data.remote.exceptions.http.UnauthorizedException;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.managers.UtilsManager;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.SpanUtils;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class LoginViewModel extends BaseViewModel {

    private AccountManager accountManager;
    private UserManager userManager;
    private UtilsManager utilsManager;

    private String email = "";
    private String password = "";

    private PublishRelay<Event> startLoginObservable = PublishRelay.create();
    private PublishRelay<Event> openRegisterObservable = PublishRelay.create();
    private PublishRelay<Event> openRecoverPasswordObservable = PublishRelay.create();
    private PublishRelay<Event> openMainObservable = PublishRelay.create();

    @Inject
    public LoginViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                          UiEvents uiEvents, AccountManager accountManager, UtilsManager utilsManager, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.accountManager = accountManager;
        this.utilsManager = utilsManager;
        this.userManager = userManager;

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
    public SpannableString getRegister() {
        String content = getResourceProvider().getString(R.string.sentence_register);
        SpannableString spString = (SpannableString) SpanUtils.underlineString(content, "Registe-se agora");
        return spString;
    }
    @Bindable
    public SpannableString getRecover() {

        String content = getResourceProvider().getString(R.string.sentence_recover_password);
        SpannableString spString = (SpannableString) SpanUtils.underlineString(content, "Recuperar");
        return spString;

    }

    @Bindable
    public boolean isLoginEnabled() {
        return !(email.isEmpty() || password.isEmpty());
    }

    public void onLoginClick() {
       login(email, password);

    }

    public void onRegisterClick() {

        if (!utilsManager.registerDataExists()){
            utilsManager.getConfigsFromServer()
                    .doOnSubscribe(this::addDisposable)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onConfigsReceived, this::onError);
        }else{
            openRegisterObservable.accept(new NavigationEvent());
        }


    }

    public void onRecoverPasswordClick() {
            openRecoverPasswordObservable.accept(new NavigationEvent());
    }

    private void onConfigsReceived(Configs configsRemote) {
        utilsManager.saveConfigs(configsRemote);
        openRegisterObservable.accept(new NavigationEvent());
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
        Answers.getInstance().logLogin(new LoginEvent()
                .putSuccess(true)
                .putCustomAttribute("email", email));

        startLoginObservable.accept(new Event());
       /* userManager.updateFirebaseToken()
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onUpdateFirebaseTokenComplete, this::onError);*/

    }

    private void onUpdateFirebaseTokenComplete(User user) {
        startLoginObservable.accept(new Event());
    }


    @Override
    protected void onError(Throwable e){
        Answers.getInstance().logLogin(new LoginEvent()
                .putSuccess(false)
                .putCustomAttribute("email", email));
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
        return startLoginObservable;
    }
    Observable<Event> observeOpenRegister(){
        return openRegisterObservable;
    }
    Observable<Event> observeOpenRecoverPassword(){
        return openRecoverPasswordObservable;
    }




}
