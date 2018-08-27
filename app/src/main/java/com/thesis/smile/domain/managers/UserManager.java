package com.thesis.smile.domain.managers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.thesis.smile.Constants;
import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.UserService;
import com.thesis.smile.domain.exceptions.NoUserLoggedException;
import com.thesis.smile.domain.mapper.UserMapper;
import com.thesis.smile.domain.mapper.UsersMapper;
import com.thesis.smile.domain.models.EnergyParams;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.domain.models.Users;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.domain.models_iota.Transfer;


import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
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

    public Single<User> updateUserProfilePic(File file){
        String token = sharedPrefs.getUserToken();
        return userService.updateUserProfilePic(token, file).map(UserMapper.INSTANCE::remoteToDomain);
    }

    public Single<User> updateUser(User user){
        String token = sharedPrefs.getUserToken();

        return userService.updateUserWithToken(token, user).map(UserMapper.INSTANCE::remoteToDomain);
    }

    public Single<User> updateFirebaseToken(){
        String token = sharedPrefs.getUserToken();
        String firebase = sharedPrefs.getFirebaseToken();
        return userService.updateFirebaseToken(token, firebase).map(UserMapper.INSTANCE::remoteToDomain);
    }

    public Single<User> updateIotaAddress(){
        String token = sharedPrefs.getUserToken();
        String address = sharedPrefs.getAddress();
        return userService.updateIotaAddress(token, address).map(UserMapper.INSTANCE::remoteToDomain);
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

    public Single<String> getAccountSeed(){ return userService.getAccountSeed();}

    public void saveAddress(String address){
        sharedPrefs.saveAddress(address);
    }

    public String getAddress(){
        return sharedPrefs.getAddress();
    }

    public void saveTransfers(List<Transfer> transfers) {
        sharedPrefs.saveTransfers(transfers);
    }

    public List<Transfer> getTransfers() {
        return sharedPrefs.getTransfers();
    }

    public Single<List<Users>>  getUsers() {
        String token = sharedPrefs.getUserToken();
        return userService.getUsers(token)
                .map(UsersMapper.INSTANCE::remoteToDomain);
    }

    public void setCurrenUserToken(String token) {
        sharedPrefs.saveUserToken(token);
    }
}
