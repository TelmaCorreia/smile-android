package com.thesis.smile.presentation.settings.user_settings;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentUserSettingsBinding;
import com.thesis.smile.presentation.authentication.register.energy.info.AutomaticSettingsInfoActivity;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.utils.KeyboardUtils;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.photos.CameraHelper;
import com.thesis.smile.presentation.utils.photos.UserImageHelper;
import com.thesis.smile.presentation.utils.views.CustomInputDialog;
import com.thesis.smile.utils.AndroidIntentHelper;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class UserSettingsFragment extends BaseFragment<FragmentUserSettingsBinding, UserSettingsViewModel> {

    private final static int REQUEST_USER_PROFILE_PICTURE = 1;
    private CustomInputDialog showSeedDialog;

    @Inject
    CameraHelper cameraHelper;

    @Inject
    AndroidIntentHelper intentHelper;

    @Inject
    UserImageHelper imageHelper;


    RxPermissions rxPermissions;

    public static UserSettingsFragment newInstance() {
        UserSettingsFragment f = new UserSettingsFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_user_settings;
    }

    @Override
    protected Class<UserSettingsViewModel> viewModelClass() {
        return UserSettingsViewModel.class;
    }

    /*@Override
    protected boolean sharedViewModel(){
        return true;
    }*/

    @Override
    protected void initViews(FragmentUserSettingsBinding binding) {
        setupUI(binding.parent, this.getActivity());
        rxPermissions = new RxPermissions(this.getActivity());

        initRadioButton();
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().openChangePasswordObservable()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    ChangePasswordActivity.launch(getContext());
                });

        getViewModel().observeRadio()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::updateRadio);


        getViewModel()
                .observeEditProfilePicture()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> editPicture());

    }

    private void initRadioButton() {
        if(getViewModel().getUserType().equals(getString(R.string.consumer))) {
            getBinding().rbConsumer.setChecked(true);
            getBinding().rbProsumer.setChecked(false);
        }else{
            getBinding().rbConsumer.setChecked(false);
            getBinding().rbProsumer.setChecked(true);
        }
    }

    private void updateRadio(Event event) {

        if(getBinding().rbConsumer.isChecked()) {
            getViewModel().setUserType(getString(R.string.consumer));
        }else{
            getViewModel().setUserType(getString(R.string.prosumer));
        }
    }

    private void editPicture(){
        if(!rxPermissions.isGranted(Manifest.permission.CAMERA)) {
            rxPermissions
                    .shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.CAMERA)
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(shouldShow -> {
                        if(shouldShow){
                            createPhotoRationalDialog().show();
                        }else{
                            requestCameraPermissions();
                        }
                    });
        }else{
            requestImage();
        }
    }

    private Dialog createPhotoRationalDialog(){
        CharSequence[] options = new CharSequence[]{
                getString(R.string.request_permission_title),
                getString(R.string.camera_permission)
        };

        return new AlertDialog.Builder(this.getActivity())
                .setItems(options, (dialog, which) -> requestCameraPermissions())
                .create();
    }

    private void requestCameraPermissions(){
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .doOnSubscribe(this::addDisposable)
                .subscribe(granted -> {
                    if (granted) {
                        requestImage();
                    }
                });
    }

    private void requestImage(){
        File tempFile = cameraHelper.setUpForPossiblePhoto();
        Intent chooserIntent = intentHelper.createRequestImageIntent(this.getActivity(), tempFile, getResourceProvider().getString(R.string.profile_picture));
        startActivityForResult(chooserIntent, REQUEST_USER_PROFILE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_USER_PROFILE_PICTURE && resultCode == RESULT_OK){
            if(data == null || data.getData() == null){
                receiveImage(true);
            }else{
                Uri imageUri = data.getData();
                receiveImageUri(imageUri, true);
            }
        }else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            receiveImageUri(UCrop.getOutput(data),false);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private void receiveImage(boolean cropBeforeUpload){
        String tempImagePath = cameraHelper.getImagePath();
        imageHelper.saveProfilePicture(tempImagePath);
        if(cropBeforeUpload) {
            cropImage();
        }else {
            uploadImage();
        }
    }

    private void receiveImageUri(Uri imageUri, boolean cropBeforeUpload){
        imageHelper.saveProfilePicture(imageUri);
        if(cropBeforeUpload){
            cropImage();
        }else{
            uploadImage();
        }
    }

    private void cropImage(){
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(80);

        UCrop.of(imageHelper.getProfilePictureUri(), imageHelper.getProfilePictureUri())
                .withAspectRatio(1, 1)
                .withOptions(options)
                .withMaxResultSize(1000, 1000)
                .start( this.getActivity());
    }

    private void uploadImage(){
        getViewModel().setProfilePicture(new File(imageHelper.getProfilePictureUri().getPath()));

        // getViewModel().setProfilePicture(imageHelper.getProfilePictureFile());
        getViewModel().setImgForeground(null);
    }

}
