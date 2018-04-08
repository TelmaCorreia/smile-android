package com.thesis.smile.domain.managers;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.UtilsService;
import com.thesis.smile.domain.models.Configs;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public Configs getConfigs(){

        return sharedPrefs.getConfigs();
    }

    public Single<Configs> getConfigsFromServer(){

        return utilsService.getConfigs();
    }


    public void saveConfigs(Configs configs) {
        sharedPrefs.saveConfigs(configs);
    }


}
