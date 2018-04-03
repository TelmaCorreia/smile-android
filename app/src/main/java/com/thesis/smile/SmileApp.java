package com.thesis.smile;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.thesis.smile.di.components.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class SmileApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

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
