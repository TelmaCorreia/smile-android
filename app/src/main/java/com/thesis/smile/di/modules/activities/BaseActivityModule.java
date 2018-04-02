package com.thesis.smile.di.modules.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.thesis.smile.di.qualifiers.ActivityContext;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class BaseActivityModule<A extends AppCompatActivity> {

    @Binds
    abstract AppCompatActivity activity(A activity);

    @Provides
    @ActivityContext
    static Context provideContext(AppCompatActivity activity) {
        return activity;
    }
}
