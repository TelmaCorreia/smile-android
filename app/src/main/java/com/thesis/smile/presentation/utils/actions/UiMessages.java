package com.thesis.smile.presentation.utils.actions;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.presentation.utils.actions.events.CloseEvent;
import com.thesis.smile.presentation.utils.actions.events.ToastEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class UiMessages {

    private AppCompatActivity activity;

    @Inject
    UiMessages(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }
}
