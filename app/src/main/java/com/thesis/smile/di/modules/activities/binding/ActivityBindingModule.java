package com.thesis.smile.di.modules.activities.binding;

import com.thesis.smile.di.modules.activities.LoginActivityModule;
import com.thesis.smile.di.modules.activities.SplashActivityModule;
import com.thesis.smile.di.scopes.ActivityScope;
import com.thesis.smile.presentation.authentication.LoginActivity;
import com.thesis.smile.presentation.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity loginActivity();
}
