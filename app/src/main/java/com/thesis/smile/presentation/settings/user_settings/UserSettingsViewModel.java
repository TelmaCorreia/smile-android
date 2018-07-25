package com.thesis.smile.presentation.settings.user_settings;

import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.gson.Gson;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.Utils;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.iota.AESCrypt;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UserSettingsViewModel extends BaseViewModel {

    private String profileImage = "";
    private File profilePictureFile;
    private Drawable imgForeground;

    private UserManager userManager;
    private User user;
    private User previousUser;
    private PublishRelay<NavigationEvent> changePasswordObservable = PublishRelay.create();
    private PublishRelay<Event> editProfilePictureObservable = PublishRelay.create();
    private PublishRelay<Event> radioChanged = PublishRelay.create();
    private PublishRelay<Event> changeRadio = PublishRelay.create();
    private PublishRelay<DialogEvent> showSeedDialog = PublishRelay.create();
    private PublishRelay<NavigationEvent> openLearnMore = PublishRelay.create();

    @Inject
    public UserSettingsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        imgForeground = VectorDrawableCompat.create(getResourceProvider().getResources(), R.drawable.ic_add_a_photo, null);
        this.userManager = userManager;
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Settings:user")
                .putContentType("Section Settings")
                .putContentId("settings_user")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail()));
        getUserFromSP();
    }

    @Bindable
    public Drawable getImgForeground(){ return imgForeground;
    }

    @Bindable
    public void setImgForeground(Drawable drawable){
        this.imgForeground=drawable;
        notifyPropertyChanged(BR.imgForeground);
    }


    @Bindable
    public String getProfileImage() {
        if (user != null){
            //return user.getUrl();
            return "http://tellma.m-iti.org:8080/users/USFmfypUzrM/image";
           // return "https://www.google.pt/search?biw=1440&bih=720&tbm=isch&sa=1&ei=4HUNW9XaBaPJgAbTprKwDA&q=desenhos&oq=des&gs_l=img.1.0.0l10.3641.4413.0.5873.3.3.0.0.0.0.160.361.1j2.3.0....0...1c.1.64.img..0.3.358...35i39k1.0.FliPRxfO1hU#imgrc=omv5ScsP6V54cM:";
        }
        return null;
    }

    public void setProfileImage(String image) {
        this.profileImage = image;
        notifyPropertyChanged(BR.profileImage);
        notifyPropertyChanged(BR.saveEnabled);
    }


    @Bindable
    public String getFirstName() {
        if (user != null){
            return user.getFirstName();
        }
        return null;
    }

    public void setFirstName(String firstName) {
        if (user != null){
            user.setFirstName(firstName);
        }
        notifyPropertyChanged(BR.firstName);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public String getLastName() {
        if (user != null){
            return user.getLastName();
        }
        return null;
    }

    public void setLastName(String lastName) {
        if (user != null){
           user.setLastName(lastName);
        }
        notifyPropertyChanged(BR.lastName);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public String getEmail() {
        if (user != null){
            return user.getEmail();
        }
        return null;
    }

    public void setEmail(String email) {
        if (user != null){
           user.setEmail(email);
        }
        notifyPropertyChanged(BR.email);
        notifyPropertyChanged(BR.saveEnabled);
    }



    public String getUserType(){
        if (user!=null){
            return user.getType();
        }
        return "";
    }
    public void setUserType(String userType) {
        if (user != null){
            user.setType(userType);
            notifyPropertiesChanged(BR.saveEnabled);
        }    }

    @Bindable
    public boolean isVisible() {
        if (user != null){
            return user.isVisible();
        }
        return true;
    }

    public void setVisible(boolean visible) {
        if (user != null){
            user.setVisible(visible);
        }
        notifyPropertyChanged(BR.visible);
        notifyPropertyChanged(BR.saveEnabled);
    }

    @Bindable
    public boolean isSaveEnabled() {
        if(user!=null) {
            return !(user.getFirstName().equals(previousUser.getFirstName())
                    && user.getLastName().equals(previousUser.getLastName())
                    && user.getEmail().equals(previousUser.getEmail())
                    && user.getType().equals(previousUser.getType())
                    && user.isVisible()==previousUser.isVisible());
        }
        return false;
    }

    private boolean isEmailValid(String email) {
        return Utils.isEmailValid(email);
    }

    public void onChangePasswordClick(){

        changePasswordObservable.accept(new NavigationEvent());

    }

    public void onConsumerClick(){
        radioChanged.accept(new Event());
    }

    public void onProsumerClick(){
        radioChanged.accept(new Event());
    }
    public void onSaveClick() {

        if (!isEmailValid(user.getEmail())){
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_invalid_email));
        }else{
            userManager.updateUser(user)
                    .compose(schedulersTransformSingleIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onUpdateComplete, this::onError);
        }

    }

    public void onLearnMoreClick(){
        openLearnMore.accept(new NavigationEvent());
    }

    private void onUpdateComplete(User user) {
        userManager.saveUser(user);
        getUiEvents().showToast(getResourceProvider().getString(R.string.msg_update_sucess));
        this.previousUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getType(), user.isVisible());
        this.user = user;
        notifyPropertyChanged(BR.saveEnabled);
    }

    public void editProfilePicture(){
        editProfilePictureObservable.accept(new Event());
    }

    Observable<NavigationEvent> openChangePasswordObservable(){
        return changePasswordObservable;
    }

    Observable<Event> observeEditProfilePicture(){
        return editProfilePictureObservable;
    }

    Observable<Event> observeRadio(){
        return radioChanged;
    }

    Observable<Event> observeChangeRadio(){
        return changeRadio;
    }
    
    Observable<DialogEvent> observeShowSeedDialog(){
        return showSeedDialog;
    }

    Observable<NavigationEvent> openLearMoreObservable(){
        return openLearnMore;
    }

    public void setProfilePicture(File profilePictureFile) {
        //TODO
        this.profilePictureFile = profilePictureFile;
        setProfileImage(profilePictureFile.getAbsolutePath());
        userManager.updateUserProfilePic(profilePictureFile)
                .doOnSubscribe(this::addDisposable)
                .subscribe();
    }



    public File getProfilePictureFile() {
        return profilePictureFile;
    }

    public void setProfilePictureFile(File profilePictureFile) {
        this.profilePictureFile = profilePictureFile;
    }

    public void getUserFromSP() {
        this.user = userManager.getCurrentUser();
        changeRadio.accept(new Event());
        this.previousUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getType(),  user.isVisible());
        notifyChange();
    }



}
