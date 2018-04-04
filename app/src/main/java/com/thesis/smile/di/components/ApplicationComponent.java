package com.thesis.smile.di.components;

import com.thesis.smile.SmileApp;
import com.thesis.smile.di.modules.ApplicationModule;
import com.thesis.smile.di.modules.NetworkModule;
import com.thesis.smile.di.modules.activities.binding.ActivityBindingModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
@Component(modules = {  ApplicationModule.class,
                        NetworkModule.class,
                        ActivityBindingModule.class,
                        AndroidSupportInjectionModule.class})
@Singleton
interface ApplicationComponent extends AndroidInjector<SmileApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SmileApp> {
    }
}
