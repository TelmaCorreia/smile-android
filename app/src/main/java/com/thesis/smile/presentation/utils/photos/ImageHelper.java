package com.thesis.smile.presentation.utils.photos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import com.thesis.smile.presentation.utils.storage.StorageHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

public class ImageHelper {

    private Context context;

    @Inject
    public ImageHelper(Context context){
        this.context = context;
    }

    public String saveToInternalStorage(String imagePath, String fileName) {
        try {
            InputStream inputStream = new FileInputStream(new File(imagePath));
            int orientation = getExifOrientationAttribute(inputStream);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            return saveToInternalStorage(bitmap, fileName, orientation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String saveToInternalStorage(Uri imageUri, String fileName) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            int orientation = getExifOrientationAttribute(inputStream);
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));
            return saveToInternalStorage(bitmap, fileName, orientation);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String fileName, int orientation) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File filePath = new File(storageDir, fileName + StorageHelper.FILE_SUFFIX);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            Bitmap rotatedBitmap = rotateBitmap(orientation, bitmapImage);
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (IOException e) {
            Log.e(ImageHelper.class.getSimpleName(), e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e) {
                Log.e(ImageHelper.class.getSimpleName(), e.getMessage());
                e.printStackTrace();
            }
        }

        return storageDir.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String fileName) {
        try {
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File filePath = new File(storageDir, fileName + StorageHelper.FILE_SUFFIX);
            return BitmapFactory.decodeStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean fileExists(String fileName) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File filePath = new File(storageDir, fileName + StorageHelper.FILE_SUFFIX);
        return filePath.exists();
    }

    public void deleteFile(String fileName) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File filePath = new File(storageDir, fileName + StorageHelper.FILE_SUFFIX);
        filePath.delete();
    }

    public File getFile(String fileName) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir, fileName + StorageHelper.FILE_SUFFIX);
    }

    public Uri getUri(String fileName) {
        return Uri.fromFile(getFile(fileName));
    }

    public Bitmap convertDrawableToBitmap(@DrawableRes int drawableId) {
        Drawable drawable = VectorDrawableCompat.create(context.getResources(), drawableId, null);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private Bitmap rotateBitmap(int orientation, Bitmap bitmap) throws IOException {
        Bitmap rotatedBitmap;
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }

        return rotatedBitmap;
    }

    private int getExifOrientationAttribute(InputStream inputStream) throws IOException {
        ExifInterface ei = new ExifInterface(inputStream);
        return ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
    }

    private Bitmap rotateImage(Bitmap bitmap, int rotation){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.setRotate(rotation);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}

