package com.thesis.smile.presentation.utils.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.presentation.utils.actions.events.AppForceUpdateEvent;
import com.thesis.smile.presentation.utils.actions.events.AppUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppUpdatesEvents {

    private PublishRelay<AppForceUpdateEvent> appForceUpdateObservable = PublishRelay.create();
    private PublishRelay<AppUpdateEvent> appUpdateObservable = PublishRelay.create();
    private BehaviorRelay<AppUpdateEvent> appUpdateDismissedObservable = BehaviorRelay.create();

    @Inject
    public AppUpdatesEvents(){}

    public void showForceUpdate(){
        appForceUpdateObservable.accept(new AppForceUpdateEvent());
    }

    public void showUpdate(){
        appUpdateObservable.accept(new AppUpdateEvent());
    }

    public Observable<AppForceUpdateEvent> observeAppForceUpdate(){
        return appForceUpdateObservable;
    }

    public Observable<AppUpdateEvent> observeAppUpdate(){
        return appUpdateObservable;
    }

    public BehaviorRelay<AppUpdateEvent> appUpdateDismissedObservable(){
        return appUpdateDismissedObservable;
    }

    public Observable<AppUpdateEvent> observeAppUpdateDismissed(){
        return appUpdateDismissedObservable;
    }

    public void openGoogleStore(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(String.format("market://details?id=%s", Utils.getProductionPackageName(context))));
        context.startActivity(intent);
    }
}
