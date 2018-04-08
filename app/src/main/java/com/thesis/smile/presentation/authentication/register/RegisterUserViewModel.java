package com.thesis.smile.presentation.authentication.register;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RegisterUserViewModel extends BaseViewModel {

    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String password = "";
    private String confirmPassword = "";
    private RegisterRequest user = new RegisterRequest();

    private PublishRelay<Event> nextObservable = PublishRelay.create();

    @Inject
    public RegisterUserViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);

    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
        notifyPropertyChanged(BR.nextEnabled);
    }

    @Bindable
    public boolean isNextEnabled() {
        return !(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty());
    }

    private boolean isEmailValid(String email) {
        return Utils.isEmailValid(email);
    }

    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword) && Utils.isPasswordValid(password);
    }

    public void onNextClick() {

        if (!isEmailValid(email)){
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_invalid_email));
        }else if (!isPasswordValid(password, confirmPassword)){
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_invalid_password));
        }else{
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            nextObservable.accept(new Event());
        }

    }

    public String getRegisterRequest() {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        return json;
    }

    Observable<Event> observeNext(){
        return nextObservable;
    }




}
