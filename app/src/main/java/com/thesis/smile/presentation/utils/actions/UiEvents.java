package com.thesis.smile.presentation.utils.actions;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.di.scopes.ActivityScope;
import com.thesis.smile.presentation.utils.actions.events.CloseEvent;
import com.thesis.smile.presentation.utils.actions.events.ToastEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class UiEvents {

    private PublishRelay<ToastEvent> toastRelay = PublishRelay.create();
    private PublishRelay<CloseEvent> closeRelay = PublishRelay.create();

    @Inject
    public UiEvents() {}

    public void close() {
        closeRelay.accept(new CloseEvent());
    }

    public void showToast(String message) {
        if (message != null && !message.isEmpty()) {
            toastRelay.accept(new ToastEvent(message));
        }
    }

    public Observable<ToastEvent> observeToastEvent() {
        return toastRelay;
    }

    public Observable<CloseEvent> observeCloseEvent() {
        return closeRelay;
    }
}
