package com.thesis.smile.di.modules.activities.binding;

import com.thesis.smile.di.modules.activities.CycleInfoActivityModule;
import com.thesis.smile.di.modules.activities.GeneralInfoActivityModule;
import com.thesis.smile.di.modules.activities.LoginActivityModule;
import com.thesis.smile.di.modules.activities.RegisterEnergyActivityModule;
import com.thesis.smile.di.modules.activities.RegisterEquipmentActivityModule;
import com.thesis.smile.di.modules.activities.RegisterUserActivityModule;
import com.thesis.smile.di.modules.activities.SplashActivityModule;
import com.thesis.smile.di.scopes.ActivityScope;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.authentication.register.energy.RegisterEnergyActivity;
import com.thesis.smile.presentation.authentication.register.RegisterUserActivity;
import com.thesis.smile.presentation.authentication.register.energy.RegisterEquipmentActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.CycleInfoActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.GeneralInfoActivity;
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

    @ActivityScope
    @ContributesAndroidInjector(modules = RegisterUserActivityModule.class)
    abstract RegisterUserActivity registerUserActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = RegisterEnergyActivityModule.class)
    abstract RegisterEnergyActivity registerEnergyActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = RegisterEquipmentActivityModule.class)
    abstract RegisterEquipmentActivity registerEquipmentActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = GeneralInfoActivityModule.class)
    abstract GeneralInfoActivity generalInfoActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = CycleInfoActivityModule.class)
    abstract CycleInfoActivity cycleInfoActivity();
}
