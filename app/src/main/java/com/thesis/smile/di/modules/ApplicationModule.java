package com.thesis.smile.di.modules;

import android.app.Application;
import android.content.Context;

import com.thesis.smile.SmileApp;
import com.thesis.smile.utils.schedulers.AppSchedulerProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ApplicationModule {

    @Binds
    abstract Application application(SmileApp smileApp);

    @Provides
    static Context provideContext(Application application) {
        return application;
    }

    @Provides
    static SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
