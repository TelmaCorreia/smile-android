package com.thesis.smile.presentation.authentication.register.energy;

import android.annotation.SuppressLint;
import android.databinding.Bindable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.SignUpEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.exceptions.http.GenericErrorException;
import com.thesis.smile.data.remote.exceptions.http.NotAcceptableException;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.domain.managers.AccountManager;
import com.thesis.smile.domain.managers.IotaManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.managers.UtilsManager;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.iota.IotaTaskManager;
import com.thesis.smile.iota.requests.GetNewAddressRequest;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.iota.AESCrypt;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalTime;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import jota.utils.IotaAPIUtils;
import jota.utils.SeedRandomGenerator;

import static jota.utils.IotaUnits.IOTA;

public class RegisterEquipmentViewModel extends BaseViewModel {

    private AccountManager accountManager;
    private UserManager userManager;
    private UtilsManager utilsManager;
    private RegisterRequest request;
    private IotaManager iotaManager;
    private String seed;
    private String encryptedSeed;
    private String smartMeterId = "";

    private boolean manual;
    private String userType;

    private PublishRelay<Event> registerObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> startMainObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> startTransactionsObservable = PublishRelay.create();
    private PublishRelay<DialogEvent> automaticConfigDialogObservable = PublishRelay.create();
    private PublishRelay<DialogEvent> seedDialogObservable = PublishRelay.create();
    private PublishRelay<Event> radioChanged = PublishRelay.create();
    private boolean loading = false;
    private boolean registerEnabled = true;

    @Inject
    public RegisterEquipmentViewModel(ResourceProvider resourceProvider,
                                      SchedulerProvider schedulerProvider, UiEvents uiEvents,
                                      AccountManager accountManager, UtilsManager utilsManager, UserManager userManager, IotaManager iotaManager) {
        super(resourceProvider, schedulerProvider, uiEvents);

        this.accountManager = accountManager;
        this.utilsManager = utilsManager;
        this.userManager = userManager;
        this.iotaManager = iotaManager;
        userType=getResourceProvider().getString(R.string.consumer);
    }


    @Bindable
    public boolean isRegisterEnabled() {
        return !smartMeterId.isEmpty() &&registerEnabled;
    }

    public void  setRegisterEnabled(boolean registerEnabled){
        this.registerEnabled = registerEnabled;
        notifyPropertyChanged(BR.registerEnabled);
    }
    @Bindable
    public boolean isEquipmentEnabled() {
        return false; //could be useful int the future
    }

    @Bindable
    public boolean isEquipmentVisible() {
        if(userType.equals(getResourceProvider().getString(R.string.consumer))) {
            return false;
        }
        return true;
    }

    @Bindable
    public String getSmartMeterId() {
        return smartMeterId;
    }

    public void setSmartMeterId(String power) {
        this.smartMeterId = power;
       // notifyPropertyChanged(BR.smartMeterId);
        notifyPropertyChanged(BR.registerEnabled);
    }
    public void onRegisterClick() {
        setLoading(true);
        userManager.getAccountSeed(smartMeterId)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onSeedReceived, this::onError);
    }

    private void onSeedReceived(String s) {
        setSeed(s);
        setLoading(false);
        automaticConfigDialogObservable.accept(new OpenDialogEvent());
        seedDialogObservable.accept(new OpenDialogEvent());

    }

    public void onGeneralInfoClick() {
        getUiEvents().showToast(getResourceProvider().getString(R.string.alert_equipment));
    }

    public void onSmartMeterInfoClick() {
        getUiEvents().showToast(getResourceProvider().getString(R.string.info_smart_meter));
    }

    public void onConsumerClick(){
        radioChanged.accept(new Event());
    }

    public void onProsumerClick(){
        radioChanged.accept(new Event());
    }

    public void setRequest(RegisterRequest request){
        this.request = request;
        register(request);
    }

    public void register(RegisterRequest request) {
        if (userType.isEmpty()){
            getUiEvents().showToast(getResourceProvider().getString(R.string.userTypeAlert));
        }else if (smartMeterId.isEmpty()){
            getUiEvents().showToast(getResourceProvider().getString(R.string.userSmartMeterAlert));
        }else{
            setLoading(true);
            setRegisterEnabled(false);
            request.setType(userType);
            request.setManual(manual);
            request.setSmartMeterId(smartMeterId);
            request.setEncryptedSeed(encryptedSeed);
            accountManager.register(request)
                    .compose(loadingTransformCompletable())
                    .compose(schedulersTransformCompletableIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onRegisterComplete, this::onError);
        }

    }

    @SuppressLint("CheckResult")
    private void onRegisterComplete() {
        Answers.getInstance().logSignUp(new SignUpEvent()
                .putSuccess(true)
                .putCustomAttribute("smid", request.getSmartMeterId())
                .putCustomAttribute("hour", LocalTime.now().getHour()));

        if (request.getFilePath()!=null && !request.getFilePath().isEmpty()){
            File file = new File (request.getFilePath());
            userManager.updateUserProfilePic(file)
                    .compose(schedulersTransformSingleIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onPicComplete, this::onError);
        }else {
            next();
        }

    }

    private void onPicComplete(User user) {
        next();
    }

    public void setRegisterRequest(RegisterRequest registerRequest){
        this.request = registerRequest;
    }

    public Configs getConfigs() {
        return utilsManager.getConfigs();
    }

    Observable<NavigationEvent> observeStartMain(){
        return startMainObservable;
    }

    Observable<NavigationEvent> observeStartTransactions(){
        return startTransactionsObservable;
    }
    Observable<DialogEvent> observeAutomaticConfigDialog(){
        return automaticConfigDialogObservable;
    }

    Observable<DialogEvent> observeSeedDialog(){
        return seedDialogObservable;
    }

    Observable<Event> observeRadio(){
        return radioChanged;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public void setUserType(String userType) {
        this.userType = userType;
        notifyPropertyChanged(BR.equipmentVisible);
        notifyPropertyChanged(BR.registerEnabled);
    }

    public void encryptSeed(String password) {
        try {
            AESCrypt aes = new AESCrypt(password);
            this.encryptedSeed = aes.encrypt(seed);
            userManager.saveSeed(seed); //save seed decrypted only in the device
        } catch (Exception e) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_seed_cypher));
        }

    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getSeed() {
        return seed;
    }

    public void alertInvalidPassword() {
        getUiEvents().showToast("Password inválida. Introduza pelo menos 5 caracteres.");
    }


    @Bindable
    public boolean isLoadingVisible(){
        return this.loading;
    }

    public void setLoading(boolean loading){
        this.loading = loading;
        notifyPropertyChanged(BR.loadingVisible);
    }


    public void next() {
        if (request.isManual()){
            startTransactionsObservable.accept(new NavigationEvent());
        }else{
            startMainObservable.accept(new NavigationEvent());
        }
    }


    @Override
    protected void onError(Throwable e){
        Answers.getInstance().logSignUp(new SignUpEvent()
                .putSuccess(false)
                .putCustomAttribute("smid", request.getSmartMeterId())
                .putCustomAttribute("hour", LocalTime.now().getHour()));
        if(e instanceof NotAcceptableException) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_smart_meter_id));
            setLoading(false);
            setRegisterEnabled(true);
        }else{
            super.onError(e);
        }
    }

}
