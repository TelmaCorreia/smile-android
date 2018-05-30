package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.UserService;
import com.thesis.smile.domain.exceptions.NoUserLoggedException;
import com.thesis.smile.domain.mapper.UserMapper;
import com.thesis.smile.domain.models.EnergyParams;
import com.thesis.smile.domain.models.User;


import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
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
        String token = sharedPrefs.getUserToken();

        return Completable.fromAction(()->
                userService.updateUserProfilePic(token, file)
                .map(UserMapper.INSTANCE::remoteToDomain));
    }

    public Single<User> updateUser(User user){
        String token = sharedPrefs.getUserToken();

        return userService.updateUserWithToken(token, user).map(UserMapper.INSTANCE::remoteToDomain);
    }

    public Single<User> updateEnergyParams(User user){
        String token = sharedPrefs.getUserToken();

        return userService.updateEnergyParamsWithToken(token, user).map(UserMapper.INSTANCE::remoteToDomain);
    }

    public User getCurrentUser(){

        return sharedPrefs.getUserData();
    }

    public String getCurrenUserToken(){

        return sharedPrefs.getUserToken();
    }

    public void saveUser(User user) {
        sharedPrefs.saveUserData(user);
    }

    public void saveSeed(String seed){
        sharedPrefs.saveSeed(seed);
    }

    public String getSeed(){

        return sharedPrefs.getSeed();
    }

}
