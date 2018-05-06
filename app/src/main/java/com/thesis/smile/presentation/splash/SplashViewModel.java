package com.thesis.smile.presentation.splash;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.exceptions.http.InternetConnectionException;
import com.thesis.smile.domain.exceptions.NoUserLoggedException;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.managers.UtilsManager;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class SplashViewModel extends BaseViewModel{

    private static final int SPLASH_DELAY = 2000;
    private UtilsManager utilsManager;
    private BehaviorRelay<NavigationEvent> openLoginObservable = BehaviorRelay.create();
    private BehaviorRelay<NavigationEvent> openMainObservable = BehaviorRelay.create();

    private final UserManager userManager;
    @Inject
    SplashViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                    UiEvents uiEvents, UserManager userManager, UtilsManager utilsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.userManager = userManager;
        this.utilsManager = utilsManager;

        showSplashAndContinue();
        //openLoginObservable.accept(new NavigationEvent());
    }

    public void showSplashAndContinue(){

        utilsManager.getConfigsFromServer()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onConfigsReceived, this::onError);

    }

    private void onIsUserLoggedIn(){
        openMainObservable.accept(new NavigationEvent());
    }

    private void onSplashError(Throwable throwable) {
        if(throwable instanceof NoUserLoggedException) {
            openLoginObservable.accept(new NavigationEvent());
            getUiEvents().close();
        }else{
            super.onError(throwable);
        }
    }

    private void onConfigsReceived(Configs configsRemote) {
        utilsManager.saveConfigs(configsRemote);

        Completable delayCompletable = Completable.complete()
                .delay(SPLASH_DELAY, TimeUnit.MILLISECONDS);
        Completable.concatArray(delayCompletable, userManager.isUserLoggedIn())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onIsUserLoggedIn, this::onSplashError);
    }

    Observable<NavigationEvent> observeOpenLogin() {
        return openLoginObservable;
    }

    Observable<NavigationEvent> observeOpenMain() {
        return openMainObservable;
    }
}
