package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.exceptions.http.InternetConnectionException;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.services.UserService;
import com.thesis.smile.domain.exceptions.NoUserLoggedException;
import com.thesis.smile.domain.exceptions.UserNotActiveException;
import com.thesis.smile.domain.models.User;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Single;

@Singleton
public class UserManager {

    private SharedPrefs sharedPrefs;
    private UserService userService;

    @Inject
    public UserManager(UserService userService, SharedPrefs sharedPrefs) {
        this.userService = userService;
        this.sharedPrefs = sharedPrefs;
    }

    public Completable isUserLoggedIn(){
        return sharedPrefs.isUserDataPresent() ? Completable.complete() : Completable.error(NoUserLoggedException::new);
    }

    public User getCurrentUser(){

        return sharedPrefs.getUserData();
    }


    public Completable fetchUserOnLogin(){
        if(sharedPrefs.isUserDataPresent()){
            return fetchUser(sharedPrefs.getUserToken());
        }else{
            return Completable.error(new NoUserLoggedException());
        }
    }

    public Completable fetchUser(String userId){
        return userService
                .getUserWithId(userId)
                .flatMap(user -> {
                    if(user.isActive())
                        return Single.just(user);
                    else{
                        return Single.error(new UserNotActiveException());
                    }
                })
                .flatMapCompletable(this::saveUser)
                .compose(handleRemoteConnectionException());
    }


    protected CompletableTransformer handleRemoteConnectionException(){
        return upstream -> upstream
                .onErrorResumeNext(throwable -> {
                    if(throwable instanceof InternetConnectionException || throwable instanceof ConnectionTimeoutException)
                        return Completable.complete();

                    return upstream;
                });
    }

    private Completable saveUser(User user) {
        return Completable.fromAction(() -> sharedPrefs.saveUserData(user));
    }


}
