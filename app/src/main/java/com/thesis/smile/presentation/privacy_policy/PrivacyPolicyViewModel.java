package com.thesis.smile.presentation.privacy_policy;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PrivacyPolicyViewModel extends BaseViewModel {

    private PublishRelay<NavigationEvent> closeObservable = PublishRelay.create();
    private PublishRelay<Event> emailObservable = PublishRelay.create();
    private PublishRelay<Event> linkObservable = PublishRelay.create();
    private UserManager userManager;

    @Inject
    public PrivacyPolicyViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.userManager = userManager;
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Privacy Policy")
                .putContentType("Section Pivacy Policy")
                .putContentId("privacy_policy")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail()));
    }

    public void onCloseClick(){
        closeObservable.accept(new NavigationEvent());
    }

    public void onLinkClick(){
        linkObservable.accept(new NavigationEvent());
    }

    public void onEmailClick(){
        emailObservable.accept(new NavigationEvent());
    }

    Observable<NavigationEvent> observeClose(){
        return closeObservable;
    }

    Observable<Event> observeLink(){
        return linkObservable;
    }

    Observable<Event> observeEmail(){
        return emailObservable;
    }

}
