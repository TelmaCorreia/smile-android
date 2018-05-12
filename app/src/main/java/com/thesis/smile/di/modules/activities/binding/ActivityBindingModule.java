package com.thesis.smile.di.modules.activities.binding;

import com.thesis.smile.di.modules.activities.AutomaticSettingsInfoActivityModule;
import com.thesis.smile.di.modules.activities.ChangePasswordModule;
import com.thesis.smile.di.modules.activities.CycleInfoActivityModule;
import com.thesis.smile.di.modules.activities.GeneralInfoActivityModule;
import com.thesis.smile.di.modules.activities.HomeDetailsActivityModule;
import com.thesis.smile.di.modules.activities.InfoPriceActivityModule;
import com.thesis.smile.di.modules.activities.LoginActivityModule;
import com.thesis.smile.di.modules.activities.MainActivityModule;
import com.thesis.smile.di.modules.activities.PrivacyPolicyActivityModule;
import com.thesis.smile.di.modules.activities.RecoverPasswordActivityModule;
import com.thesis.smile.di.modules.activities.RegisterEnergyActivityModule;
import com.thesis.smile.di.modules.activities.RegisterEquipmentActivityModule;
import com.thesis.smile.di.modules.activities.RegisterUserActivityModule;
import com.thesis.smile.di.modules.activities.SettingsActivityModule;
import com.thesis.smile.di.modules.activities.SplashActivityModule;
import com.thesis.smile.di.modules.activities.TimersActivityModule;
import com.thesis.smile.di.modules.activities.TransactionDetailsActivityModule;
import com.thesis.smile.di.scopes.ActivityScope;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.authentication.recover_pass.RecoverPasswordActivity;
import com.thesis.smile.presentation.authentication.register.energy.RegisterEnergyActivity;
import com.thesis.smile.presentation.authentication.register.RegisterUserActivity;
import com.thesis.smile.presentation.authentication.register.energy.RegisterEquipmentActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.AutomaticSettingsInfoActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.CycleInfoActivity;
import com.thesis.smile.presentation.authentication.register.energy.info.GeneralInfoActivity;
import com.thesis.smile.presentation.main.MainActivity;
import com.thesis.smile.presentation.main.home.HomeDetailsActivity;
import com.thesis.smile.presentation.info_price.InfoPriceActivity;
import com.thesis.smile.presentation.privacy_policy.PrivacyPolicyActivity;
import com.thesis.smile.presentation.timers.TimersActivity;
import com.thesis.smile.presentation.transaction_details.TransactionDetailsActivity;
import com.thesis.smile.presentation.settings.SettingsActivity;
import com.thesis.smile.presentation.settings.user_settings.ChangePasswordActivity;
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
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

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

    @ActivityScope
    @ContributesAndroidInjector(modules = HomeDetailsActivityModule.class)
    abstract HomeDetailsActivity homeDetailsActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = SettingsActivityModule.class)
    abstract SettingsActivity settingsActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = RecoverPasswordActivityModule.class)
    abstract RecoverPasswordActivity recoverPasswordActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = ChangePasswordModule.class)
    abstract ChangePasswordActivity changePasswordActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = TimersActivityModule.class)
    abstract TimersActivity timersActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = InfoPriceActivityModule.class)
    abstract InfoPriceActivity infoPriceActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = PrivacyPolicyActivityModule.class)
    abstract PrivacyPolicyActivity privacyPolicyActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = TransactionDetailsActivityModule.class)
    abstract TransactionDetailsActivity transactionDetailsActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = AutomaticSettingsInfoActivityModule.class)
    abstract AutomaticSettingsInfoActivity automaticSettingsInfoActivity();
}
