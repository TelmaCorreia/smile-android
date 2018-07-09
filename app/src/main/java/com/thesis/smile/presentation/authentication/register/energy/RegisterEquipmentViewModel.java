package com.thesis.smile.presentation.authentication.register.energy;

import android.databinding.Bindable;

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
        return isEquipmentVisible()? !smartMeterId.isEmpty() &&registerEnabled:registerEnabled;
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
        automaticConfigDialogObservable.accept(new OpenDialogEvent());
        seedDialogObservable.accept(new OpenDialogEvent());
        registerObservable.accept(new Event());
    }

    public void onGeneralInfoClick() {
        getUiEvents().showToast(getResourceProvider().getString(R.string.alert_equipment));
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

    private void onRegisterComplete() {
        //Generate an address to receive money (only one)
        generateNewAddress();
        //next();
        if (request.getPicture()!=null){
            userManager.updateUserProfilePic(request.getPicture())
                    .compose(loadingTransformCompletable())
                    .compose(schedulersTransformCompletableIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onPicComplete, this::onError);
        }

    }

    public void generateNewAddress() {
        setRegisterEnabled(false);
        iotaManager.generateNewAddress();
    }

    private void onPicComplete() {
        startMainObservable.accept(new NavigationEvent());
    }

    public void setRegisterRequest(RegisterRequest registerRequest){
        this.request = registerRequest;
    }

    public Configs getConfigs() {
        return utilsManager.getConfigs();
    }
    Observable<Event> observeRegister(){
        return registerObservable;
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

    public String generateSeed(){
        this.setSeed(SeedRandomGenerator.generateNewSeed());
        return getSeed();
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

    public void attachNewAddress(String address) {
        iotaManager.attachNewAddress(address);
    }

    @Bindable
    public boolean isLoadingVisible(){
        return this.loading;
    }

    public void setLoading(boolean loading){
        this.loading = loading;
        notifyPropertyChanged(BR.loadingVisible);
    }

    public void saveAddress(String address) {
        userManager.saveAddress(address);

    }

    public void next() {
        if (request.isManual()){
            startTransactionsObservable.accept(new NavigationEvent());
        }else{
            startMainObservable.accept(new NavigationEvent());
        }
    }

    public void sendAddress(){
        getUiEvents().showToast("Endereço gerado, verifique a sua conta dentro de 1h!");
        next();
    }

    @Override
    protected void onError(Throwable e){
        if(e instanceof NotAcceptableException) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_smart_meter_id));
            setLoading(false);
        }else{
            super.onError(e);
            getUiEvents().showToast("Erro ao enviar o endereço. Vá às definições da conta IOTA e envie um novo endereço!");
            next();
        }
    }

    private void onAddressUpdated(User user) {

    }

}
