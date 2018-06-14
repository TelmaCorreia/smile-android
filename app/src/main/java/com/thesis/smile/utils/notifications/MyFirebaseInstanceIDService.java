package com.thesis.smile.utils.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.thesis.smile.Constants;
import com.thesis.smile.data.preferences.SharedPrefs;

import javax.inject.Inject;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static String TAG = MyFirebaseInstanceIDService.class.getCanonicalName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(Constants.FIREBASE_TOKEN, refreshedToken).apply();
    }

}
