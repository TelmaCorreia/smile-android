package com.thesis.smile.data.preferences;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.thesis.smile.data.remote.models.UserRemote;

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
    public String getUserToken(){
        return getStringPreference(Keys.USER_TOKEN);
    }

    public void saveUserToken(String userToken){
        saveStringPreference(Keys.USER_TOKEN, userToken);
    }

    public void saveUserData(UserRemote user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        saveStringPreference(Keys.USER_DATA, json);

    }

    public UserRemote getUserData(){
        Gson gson = new Gson();
        String json = getStringPreference(Keys.USER_DATA);
        return gson.fromJson(json, UserRemote.class);
    }

    private class Keys {
        static final String USER_TOKEN = "userToken";
        static final String USER_DATA = "userData";
    }
}
