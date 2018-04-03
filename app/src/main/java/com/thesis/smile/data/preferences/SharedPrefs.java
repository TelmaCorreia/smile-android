package com.thesis.smile.data.preferences;

import android.content.Context;

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

    public String getUserToken(){
        return getStringPreference(Keys.USER_TOKEN);
    }

    public void saveUserToken(String userToken){
        saveStringPreference(Keys.USER_TOKEN, userToken);
    }

    private class Keys {
        static final String USER_TOKEN = "userToken";
    }
}
