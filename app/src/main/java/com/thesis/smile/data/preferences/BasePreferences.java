package com.thesis.smile.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

abstract class BasePreferences{

    abstract String getPreferenceName();

    final Context context;

    BasePreferences(Context context) {
        this.context = context;
    }
    void saveStringPreference(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.apply();
    }

    String getStringPreference(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(key, null);
    }

    void saveBooleanPreference(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.apply();
    }

    boolean getBooleanPreference(String key, boolean defaultValue) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean(key, defaultValue);
    }

    void saveIntPreference(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.apply();
    }

    int getIntPreference(String key, int defaultValue) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getInt(key, defaultValue);
    }

    void deletePreferences(String... keys){
        for(String key : keys){
            deletePreference(key);
        }
    }

    void deletePreference(String key) {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(key);
        editor.apply();
    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.edit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(getPreferenceName(), Context.MODE_PRIVATE);
    }

    void clear() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }
}
