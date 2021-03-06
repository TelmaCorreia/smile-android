package com.thesis.smile.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thesis.smile.Constants;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.domain.models_iota.Transfer;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class SharedPrefs extends BasePreferences {

    @Inject
    public SharedPrefs(Context context) {
        super(context);
    }

    @Override
    String getPreferenceName() {
        return context.getPackageName();
    }


    public boolean isUserDataPresent(){
        return !TextUtils.isEmpty(getStringPreference(Keys.USER_DATA));
    }

    public boolean isConfigsDataPresent(){
        return !TextUtils.isEmpty(getStringPreference(Keys.CONFIGS));
    }

    public String getUserToken(){
        return getStringPreference(Keys.USER_TOKEN);
    }

    public String getUserAuthorizationHeader(){
        return getStringPreference(Keys.USER_AUTH_HEADER);
    }

    public void saveUserAuthorizationHeader(String authHeader){
        saveStringPreference(Keys.USER_AUTH_HEADER, authHeader);
    }
    public void saveUserToken(String userToken){
        saveStringPreference(Keys.USER_TOKEN, userToken);
    }

    public void saveUserData(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        saveStringPreference(Keys.USER_DATA, json);

    }

    public User getUserData(){
        Gson gson = new Gson();
        String json = getStringPreference(Keys.USER_DATA);
        return gson.fromJson(json, User.class);
    }

    public void saveConfigs(Configs configs){
        Gson gson = new Gson();
        String json = gson.toJson(configs);
        saveStringPreference(Keys.CONFIGS, json);

    }

    public void saveSeed(String seed){
        saveStringPreference(Keys.SEED, seed);

    }

    public String getSeed(){
      return getStringPreference(Keys.SEED);
    }

    public void saveAddress(String address){
        saveStringPreference(Keys.ADDRESS, address);
    }

    public String getAddress(){
        return getStringPreference(Keys.ADDRESS);
    }

    public Configs getConfigs(){
        Gson gson = new Gson();
        String json = getStringPreference(Keys.CONFIGS);
        return gson.fromJson(json, Configs.class);
    }

    public void deleteUserData() {
        deletePreferences(Keys.USER_TOKEN, Keys.USER_DATA, Keys.USER_AUTH_HEADER, Keys.USER_TOKEN, Keys.SEED, Keys.ADDRESS, Keys.TRANSFERS);
    }

    public String getFirebaseToken(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return preferences.getString(Constants.FIREBASE_TOKEN, "");
    }

    public void saveFirebaseToken(String refreshedToken) {
        saveStringPreference(Keys.FIREBASE_TOKEN, refreshedToken);
    }

    public void saveTransfers(List<Transfer> transfers) {
        Gson gson = new Gson();
        String json = gson.toJson(transfers);
        saveStringPreference(Keys.TRANSFERS, json);

    }

    public List<Transfer> getTransfers(){
        Gson gson = new Gson();
        String json = getStringPreference(Keys.TRANSFERS);
        Type listType = new TypeToken<List<Transfer>>(){}.getType();

        return gson.fromJson(json, listType);
    }

    private class Keys {
        static final String USER_TOKEN = "userToken";
        static final String USER_AUTH_HEADER = "userAuthHeader";
        static final String USER_DATA = "userData";
        static final String CONFIGS = "configs";
        static final String SEED = "seed";
        static final String ADDRESS = "address";
        static final String TRANSFERS = "transfers";
        static final String FIREBASE_TOKEN = "fireBaseToken";
    }
}
