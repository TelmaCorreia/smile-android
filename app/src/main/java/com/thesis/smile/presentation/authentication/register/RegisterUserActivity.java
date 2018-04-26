package com.thesis.smile.presentation.authentication.register;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityRegisterUserBinding;
import com.thesis.smile.presentation.authentication.register.energy.RegisterEnergyActivity;
import com.thesis.smile.presentation.authentication.register.energy.RegisterEquipmentActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.photos.CameraHelper;
import com.thesis.smile.presentation.utils.photos.UserImageHelper;
import com.thesis.smile.presentation.utils.views.CustomDialog;
import com.thesis.smile.utils.AndroidIntentHelper;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import javax.inject.Inject;

public class RegisterUserActivity extends BaseActivity<ActivityRegisterUserBinding, RegisterUserViewModel> {

    private final static int REQUEST_USER_PROFILE_PICTURE = 1;
    private CustomDialog dialogShareData;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterUserActivity.class);
        context.startActivity(intent);
    }

    @Inject
    CameraHelper cameraHelper;

    @Inject
    AndroidIntentHelper intentHelper;

    @Inject
    UserImageHelper imageHelper;


    RxPermissions rxPermissions;

    @Override
    protected int layoutResId() {
        return R.layout.activity_register_user;
    }

    @Override
    protected Class viewModelClass() {
        return RegisterUserViewModel.class;
    }

    @Override
    protected void initViews(ActivityRegisterUserBinding binding) {
        rxPermissions = new RxPermissions(this);

        if (BuildConfig.DEBUG){
            getViewModel().setFirstName("Telma");
            getViewModel().setLastName("Correia");
            getViewModel().setEmail("tc@gmail.com");
            getViewModel().setPassword("Smile2018@");
            getViewModel().setConfirmPassword("Smile2018@");

        }
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

/*        getViewModel().observeNext()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event ->
                    RegisterEnergyActivity.launch(this, getViewModel().getRegisterRequest()));*/

        getViewModel()
                .observeEditProfilePicture()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> editPicture());

        getViewModel().observeShareDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::shareDataDialogEvent);

    }

    private void editPicture(){
        if(!rxPermissions.isGranted(Manifest.permission.CAMERA)) {
            rxPermissions
                    .shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
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

        return new AlertDialog.Builder(this)
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
        Intent chooserIntent = intentHelper.createRequestImageIntent(this, tempFile, getResourceProvider().getString(R.string.profile_picture));
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
                .start( this);
    }

    private void uploadImage(){
        getViewModel().setProfilePicture(new File(imageHelper.getProfilePictureUri().getPath()));

       // getViewModel().setProfilePicture(imageHelper.getProfilePictureFile());
        getViewModel().setImgForeground(null);
    }

    private void shareDataDialogEvent(DialogEvent event){
        if(dialogShareData == null){
            dialogShareData = new CustomDialog(RegisterUserActivity.this);
            dialogShareData.setTitle(R.string.share_data_tilte);
            dialogShareData.setMessage(R.string.share_data_description);
            dialogShareData.setSecondMessage(R.string.share_data_changes);
            dialogShareData.setOkButtonText(R.string.button_allow);
            dialogShareData.setCloseButtonText(R.string.button_not_allow);
            dialogShareData.setDismissible(true);
            dialogShareData.setOnOkClickListener(() -> {getViewModel().setShare(true);  dialogShareData.dismiss(); next();});
            dialogShareData.setOnCloseClickListener(() ->{getViewModel().setShare(false); dialogShareData.dismiss(); next();});
        }
        if(event instanceof OpenDialogEvent){
            dialogShareData.show();
        }else{
            dialogShareData.dismiss();
        }
    }

    public void next(){
        RegisterEnergyActivity.launch(this, getViewModel().getRegisterRequest());
    }

}
