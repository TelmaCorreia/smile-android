package com.thesis.smile.domain.managers;

import android.util.Log;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.models.LoginRemote;
import com.thesis.smile.data.remote.services.LoginService;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;

@Singleton
public class AccountManager {

    public static final String TAG = AccountManager.class.getSimpleName();
    private SharedPrefs sharedPrefs;
    private LoginService loginService;

    @Inject
    public AccountManager(LoginService loginService, SharedPrefs sharedPrefs) {
        this.loginService = loginService;
        this.sharedPrefs = sharedPrefs;
    }

    public Completable login(String email, String password) {
        return loginService.login(email, password)
                .flatMapCompletable(this::onLoginSuccess);
    }

    public Completable register(String firstName, String lastName, String email, String password,
                                String type, int power, int category, int cycle, int tariff) {
        return loginService.register(email, password, firstName, lastName, type, power, category, cycle, tariff )
                .flatMapCompletable(this::onLoginSuccess);
    }

    private Completable onLoginSuccess(LoginRemote loginRemote){
        Log.d(TAG,"Login/Register Successful");

        return Completable.fromAction(() -> sharedPrefs.saveUserToken(loginRemote.getToken()));
    }
}
