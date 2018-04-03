package com.thesis.smile.presentation.utils.actions;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.thesis.smile.di.scopes.ActivityScope;

import javax.inject.Inject;


@ActivityScope
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
