package com.thesis.smile.domain.managers;

import android.util.Log;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.models.LoginRemote;
import com.thesis.smile.data.remote.services.LoginService;

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

    private Completable onLoginSuccess(LoginRemote loginRemote){
        Log.d(TAG,"Login/Register Successful");

        sharedPrefs.saveUserToken( loginRemote.getToken());
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {

            }
        };
        //return usersRepository.saveUser(UserMapper.INSTANCE.remoteToLocal(loginRemote.getUserRemote()));
    }
}
