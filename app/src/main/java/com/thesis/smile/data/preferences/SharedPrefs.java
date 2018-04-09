package com.thesis.smile.data.preferences;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.User;

import javax.inject.Inject;

public class SharedPrefs extends BasePreferences {

    @Inject
    SharedPrefs(Context context) {
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

    public Configs getConfigs(){
        Gson gson = new Gson();
        String json = getStringPreference(Keys.CONFIGS);
        return gson.fromJson(json, Configs.class);
    }

    public void deleteUserData() {
        deletePreferences(Keys.USER_TOKEN, Keys.USER_DATA);
    }

    private class Keys {
        static final String USER_TOKEN = "userToken";
        static final String USER_DATA = "userData";
        static final String CONFIGS = "configs";
    }
}
