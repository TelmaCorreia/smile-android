package com.thesis.smile.presentation.utils.photos;

import android.os.Environment;
import android.util.Log;

import com.thesis.smile.presentation.utils.storage.StorageHelper;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

public class CameraHelper {

    private String mCurrentPhotoPath;

    private static final String TAG = CameraHelper.class.getSimpleName();

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    @Inject
    public CameraHelper(){
        mAlbumStorageDirFactory = new AlbumDirFactory();
    }

    public File setUpForPossiblePhoto(){
        try {
            File file = setUpPhotoFile();
            mCurrentPhotoPath = file.getAbsolutePath();
            return file;
        }catch (IOException e){
            return null;
        }
    }

    public String getImagePath(){
        return mCurrentPhotoPath;
    }

    private String getAlbumName() {
        return StorageHelper.DIR_NAME;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d(TAG, "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(TAG, "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        File albumF = getAlbumDir();
        return File.createTempFile(StorageHelper.FILE_PREFIX, StorageHelper.FILE_SUFFIX, albumF);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    abstract class AlbumStorageDirFactory {
        public abstract File getAlbumStorageDir(String albumName);
    }

    private final class AlbumDirFactory extends AlbumStorageDirFactory {

        @Override
        public File getAlbumStorageDir(String albumName) {
            return new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                    ),
                    albumName
            );
        }
    }
}