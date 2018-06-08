package com.thesis.smile.presentation.authentication.register;

import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public class RegisterUserViewModel extends BaseViewModel {

    private String profileImage = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String password = "";
    private String confirmPassword = "";
    private File profilePictureFile;
    private Drawable imgForeground;
    private RegisterRequest user = new RegisterRequest();
    private boolean share;

    private PublishRelay<DialogEvent> shareDialogObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> nextObservable = PublishRelay.create();
    private PublishRelay<Event> editProfilePictureObservable = PublishRelay.create();

    @Inject
    public RegisterUserViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents) {
        super(resourceProvider, schedulerProvider, uiEvents);

        imgForeground = VectorDrawableCompat.create(getResourceProvider().getResources(), R.drawable.ic_add_a_photo, null);

    }

    @Bindable
    public Drawable getImgForeground(){

        return imgForeground;

    }

    @Bindable
    public void setImgForeground(Drawable drawable){
        this.imgForeground=drawable;
        notifyPropertyChanged(BR.imgForeground);
    }


    @Bindable
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String image) {
        this.profileImage = image;
        notifyPropertyChanged(BR.profileImage);
        notifyPropertyChanged(BR.nextEnabled);
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
            shareDialogObservable.accept(new OpenDialogEvent());
        }

    }

    public void setUser(){
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setVisible(share);
        if (profilePictureFile!=null) {user.setPicture(profilePictureFile);}
        nextObservable.accept(new NavigationEvent());
    }

    public String getRegisterRequest() {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        return json;
    }

    public void editProfilePicture(){
        editProfilePictureObservable.accept(new Event());

    }

    Observable<NavigationEvent> observeNext(){
        return nextObservable;
    }

    Observable<Event> observeEditProfilePicture(){
        return editProfilePictureObservable;
    }

    public void setProfilePicture(File profilePictureFile) {
        //TODO
        this.profilePictureFile = profilePictureFile;
        setProfileImage(profilePictureFile.getAbsolutePath());
        notifyPropertyChanged(BR.profileImage);
       /* userManager.updateUserProfilePic(file)
                .doOnSubscribe(d -> {
                    addDisposable(d);
                    endEdition();
                })
                .subscribe(() -> {}, Timber::e);*/
    }

    public File getProfilePictureFile() {
        return profilePictureFile;
    }

    public void setProfilePictureFile(File profilePictureFile) {
        this.profilePictureFile = profilePictureFile;
    }

    Observable<DialogEvent> observeShareDialog(){
        return shareDialogObservable;
    }


    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }


}
