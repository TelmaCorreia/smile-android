package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.exceptions.http.InternetConnectionException;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.services.UserService;
import com.thesis.smile.domain.exceptions.NoUserLoggedException;
import com.thesis.smile.domain.exceptions.UserNotActiveException;
import com.thesis.smile.domain.mapper.UserMapper;
import com.thesis.smile.domain.models.User;


import java.io.File;

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

    public Completable updateUserProfilePic(File file){
        String currentUserId = sharedPrefs.getUserToken();

        return Completable.fromAction(()->
                userService.updateUserProfilePic(currentUserId, file)
                .map(UserMapper.INSTANCE::remoteToDomain));
    }

    public User getCurrentUser(){

        return sharedPrefs.getUserData();
    }

    public String getCurrenUserToken(){

        return sharedPrefs.getUserToken();
    }

    private Completable saveUser(User user) {
        return Completable.fromAction(() -> sharedPrefs.saveUserData(user));
    }


}
