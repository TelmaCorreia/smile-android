package com.thesis.smile;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.thesis.smile.di.components.DaggerApplicationComponent;
import com.thesis.smile.iota.service.PaymentService;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.fabric.sdk.android.Fabric;

public class SmileApp extends DaggerApplication {
    public static char[] seed = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        seed=null;
        AndroidThreeTen.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }

}
