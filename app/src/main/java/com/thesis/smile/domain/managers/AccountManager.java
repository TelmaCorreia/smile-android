package com.thesis.smile.domain.managers;

import android.util.Log;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.models.LoginRemote;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.data.remote.services.LoginService;
import com.thesis.smile.data.remote.services.UserService;
import com.thesis.smile.domain.mapper.UserMapper;

import java.io.File;
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
    private UserService userService;

    @Inject
    public AccountManager(LoginService loginService, SharedPrefs sharedPrefs) {
        this.loginService = loginService;
        this.sharedPrefs = sharedPrefs;
    }

    public Completable login(String email, String password) {
        return loginService.login(email, password)
                .flatMapCompletable(this::onLoginSuccess);
    }

    public Completable logout(){
        return Completable.fromAction(() -> sharedPrefs.deleteUserData());
    }

    public Completable register(RegisterRequest request) {
        return loginService.register(request)
                .flatMapCompletable(this::onLoginSuccess);
    }

    private Completable onLoginSuccess(LoginRemote loginRemote){
       Log.d(TAG,"Login/Register Successful");
        sharedPrefs.saveUserToken(loginRemote.getToken());
        return Completable.fromAction(() -> sharedPrefs.saveUserData(UserMapper.INSTANCE.remoteToDomain(loginRemote.getUserRemote())));
    }

    public Completable recoverPasswordStep1(String email, String password) {
        return loginService.recoverPasswordStep1(email, password);
    }

    public Completable recoverPasswordStep2(String email, String pin) {
        return loginService.recoverPasswordStep2(email, pin);
    }

    public Completable changePassword(String oldPassword, String newPassword) {
        String token = sharedPrefs.getUserToken();
        return loginService.changePassword(token, oldPassword, newPassword);
    }
}
