package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.exceptions.http.InternetConnectionException;
import com.thesis.smile.data.remote.models.ConfigsRemote;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.data.remote.services.UserService;
import com.thesis.smile.data.remote.services.UtilsService;
import com.thesis.smile.domain.exceptions.NoRegisterDataException;
import com.thesis.smile.domain.exceptions.NoUserLoggedException;
import com.thesis.smile.domain.exceptions.UserNotActiveException;
import com.thesis.smile.presentation.utils.actions.Utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Single;

@Singleton
public class UtilsManager {

    private SharedPrefs sharedPrefs;
    private UtilsService utilsService;

    @Inject
    public UtilsManager(UtilsService utilsService, SharedPrefs sharedPrefs) {
        this.utilsService = utilsService;
        this.sharedPrefs = sharedPrefs;
    }

    public boolean registerDataExists(){
        return sharedPrefs.isConfigsDataPresent();
    }

    public ConfigsRemote getConfigs(){

        return sharedPrefs.getConfigs();
    }

    public Single<ConfigsRemote> getConfigsFromServer(){

        return utilsService.getConfigs();
    }


    public void saveConfigs(ConfigsRemote configs) {
        sharedPrefs.saveConfigs(configs);
    }


}
