package com.thesis.smile.presentation.utils.photos;

import android.net.Uri;

import java.io.File;

import javax.inject.Inject;

public class UserImageHelper {

    public final static String PROFILE_PICTURE_FILE_NAME = "profilePicture";

    private final ImageHelper imageHelper;

    @Inject
    public UserImageHelper(ImageHelper imageHelper){
        this.imageHelper = imageHelper;
    }

    public void deleteUserImage(){
        imageHelper.deleteFile(PROFILE_PICTURE_FILE_NAME);
    }

    public void saveProfilePicture(String imageTempPath){
        imageHelper.saveToInternalStorage(imageTempPath, PROFILE_PICTURE_FILE_NAME);
    }

    public void saveProfilePicture(Uri imageUri){
        imageHelper.saveToInternalStorage(imageUri, PROFILE_PICTURE_FILE_NAME);
    }

    public boolean profilePictureExists() {
        return imageHelper.fileExists(PROFILE_PICTURE_FILE_NAME);
    }

    public File getProfilePictureFile(){
        return imageHelper.getFile(PROFILE_PICTURE_FILE_NAME);
    }

    public Uri getProfilePictureUri() {
        return imageHelper.getUri(PROFILE_PICTURE_FILE_NAME);
    }
}

