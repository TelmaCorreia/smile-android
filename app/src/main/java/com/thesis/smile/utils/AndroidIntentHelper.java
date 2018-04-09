package com.thesis.smile.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AndroidIntentHelper {

    @Inject
    public AndroidIntentHelper() {
    }

    public Intent createRequestImageIntent(Context context, File imageFile, String chooserTitle) {

        Intent cameraIntent = null;
        if(imageFile != null) {
            Uri imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);
            cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        List<Intent> yourIntentsList = new ArrayList<>();

        if(cameraIntent != null){
            List<ResolveInfo> listCam = context.getPackageManager().queryIntentActivities(cameraIntent, 0);
            for (ResolveInfo res : listCam) {
                final Intent finalIntent = new Intent(cameraIntent);
                finalIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                yourIntentsList.add(finalIntent);
            }
        }

        Intent chooserIntent = Intent.createChooser(galleryIntent, chooserTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, yourIntentsList.toArray(new Parcelable[yourIntentsList.size()]));

        return chooserIntent;
    }
}
