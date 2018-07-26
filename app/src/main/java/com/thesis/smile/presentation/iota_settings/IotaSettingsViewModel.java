package com.thesis.smile.presentation.iota_settings;

import android.databinding.Bindable;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.IotaManager;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.domain.models_iota.Transfer;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.iota.AESCrypt;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class IotaSettingsViewModel extends BaseToolbarViewModel {

    List<Address> addresses;
    private IotaManager iotaManager;
    private UserManager userManager;
    private TransactionsManager transactionsManager;
    private User user;
    private PublishRelay<DialogEvent> showSeedDialog = PublishRelay.create();
    private PublishRelay<DialogEvent> insertPassSeedDialog = PublishRelay.create();
    private PublishRelay<Event> startPaymentServiceEvent = PublishRelay.create();
    private String seed = "";
    private boolean seedVisible = false;
    private boolean screenBlocked = false;
    private String balanceIota = "";
    private String balanceEuro = "";
    private boolean isLoading = false;
    private List<Transaction> transactions;
    private final int addressQuantity=10;
    private int askAddressTimes = 0;
    private boolean gettingAddresses = false;
    private String addressesQuantity = "";
    private String progressText = askAddressTimes+"/"+addressQuantity;


    @Inject
    public IotaSettingsViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager, IotaManager iotaManager, TransactionsManager transactionsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.iotaManager = iotaManager;
        this.transactionsManager=transactionsManager;
        this.addresses = new ArrayList<>();
        this.userManager = userManager;
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("IotaSettings")
                .putContentType("Section Iota")
                .putContentId("iota_settings")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail())
                .putCustomAttribute("hour", LocalTime.now().getHour()));
        getAddressesFromServer();
    }

    private void getAddressesFromServer() {
        transactionsManager.getAddressesQuantity()
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onAddressesReceived, this::onError);
    }

    private void onAddressesReceived(String s) {
        setAddresses(s);
    }

    @Bindable
    public String getSeed() {
        return this.seed;
    }

    public void setSeed(String seed) {
        this.seedVisible=true;
        this.seed=seed;
        notifyPropertiesChanged(BR.seed, BR.seedVisible, BR.hideSeedVisible);

    }

    @Bindable
    public String getAddresses() {
        return this.addressesQuantity;
    }

    public void setAddresses(String addresses) {
        this.addressesQuantity =addresses;
        notifyPropertyChanged(BR.addresses);
    }

    @Bindable
    public String getBalanceEuro() {
        return this.balanceEuro;
    }

    public void setBalanceEuro(String euro) {
        this.balanceEuro=euro;
        notifyPropertyChanged(BR.balanceEuro);
    }

    @Bindable
    public String getBalanceIota() {
        return this.balanceIota;
    }

    public void setBalanceIota(String iota) {
        this.balanceIota=iota;
        notifyPropertyChanged(BR.balanceIota);
    }

    @Bindable
    public int getSeedVisible() {
        return !seedVisible? View.VISIBLE:View.GONE;
    }

    @Bindable
    public int getHideSeedVisible() {
        return seedVisible? View.VISIBLE:View.GONE;
    }


    @Bindable
    public boolean getLayoutEnabled() {
        return !screenBlocked;
    }
    @Bindable
    public int getBlockScreenVisible() {
        return screenBlocked? View.VISIBLE:View.GONE;
    }

    public void setScreenBlocked(boolean screenBlocked){
        this.screenBlocked = screenBlocked;
        notifyPropertiesChanged(BR.blockScreenVisible, BR.layoutEnabled);
    }

    @Bindable
    public boolean isProgress(){
        return isLoading;
    }

    public void setProgress(boolean loading){
        this.isLoading = loading;
        notifyPropertyChanged(BR.progress);
    }

    public void generateNewAddress() {
        iotaManager.generateNewAddress(seed);
    }

    public void attachNewAddress(String s) {
        iotaManager.attachNewAddress(seed, s);
        transactionsManager.insertAddress(new com.thesis.smile.domain.models.Address(s))
                            .doOnSubscribe(this::addDisposable)
                            .subscribe(this::onAddressInserted, this::onError);
    }

    private void onAddressInserted(String s) {
        this.askAddressTimes++;
        if (askAddressTimes<addressQuantity){
            generateNewAddress();
        }else{
            setScreenBlocked(false);
            this.askAddressTimes = 0;
            getAddressesFromServer();
        }
    }

    public void getAccountData() {
        iotaManager.getAccountData(seed);
    }

    public void onPayMyBillsClick(){
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("IotaSettings: pay bills")
                .putContentType("Section Iota")
                .putContentId("iota_settings_pay_bills")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail())
                .putCustomAttribute("hour", LocalTime.now().getHour()));

        setGettingAddresses(false);
        transactionsManager.getTransactionsToPay()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onTransactionsToPayReceived, this::onError);
    }

    private void onTransactionsToPayReceived(List<Transaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
        if (transactions.size()>0){
            setScreenBlocked(true);
            startPaymentServiceEvent.accept(new Event());
        }else{
            getUiEvents().showToast(getResourceProvider().getString(R.string.no_transactions_to_pay));
        }

    }

    public void onAttachAddressClick(){
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("IotaSettings: attach address")
                .putContentType("Section Iota")
                .putContentId("iota_settings_attach_address")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail())
                .putCustomAttribute("hour", LocalTime.now().getHour()));
        setGettingAddresses(true);
        setScreenBlocked(true);
        iotaManager.generateNewAddress(seed);

    }

    public void message(String  msg){
        getUiEvents().showToast(msg);
    }

    public void sendTransfer(Transaction transaction, String iotaBalance){
        iotaManager.sendTransfer(transaction.getAddress(), seed, iotaBalance);
    }

    public void updateState(int index){

        if (index>0 && index<transactions.size()){
            transactions.get(index).setState(Constants.STATUS_ATTACHED);
            transactionsManager.updateTransaction(transactions.get(index))
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onTransactionStateUpdated, this::onError);
        }
    }

    @Override
    public void onError(Throwable e){
        setScreenBlocked(false);
        super.onError(e);
    }
    private void onTransactionStateUpdated(String s) {
        setScreenBlocked(false);
        getUiEvents().showToast("Transação paga");
    }

    public void onShowSeedClick(){
        showSeedDialog.accept(new DialogEvent());
    }

    public void onHideSeedClick(){
        this.seedVisible = false;
        notifyPropertiesChanged(BR.seedVisible, BR.hideSeedVisible);
    }

    public void decryptSeed(String input) {
        String seed = "";
        this.user = userManager.getCurrentUser();
        if (user!=null && user.getEncryptedSeed()!=null){
            try {
                AESCrypt aes = new AESCrypt(input);
                seed = aes.decrypt(user.getEncryptedSeed());
                setSeed(seed);
                userManager.saveSeed(seed);
            } catch (Exception e) {
                getUiEvents().showToast(getResourceProvider().getString(R.string.err_seed_decypher));
            }
        }
    }

    Observable<DialogEvent> observeShowSeedDialog(){
        return showSeedDialog;
    }

    Observable<DialogEvent> observeInsertPasswordSeedDialog(){
        return insertPassSeedDialog;
    }

    Observable<Event> observeStartPaymentServiceEvent(){
        return startPaymentServiceEvent;
    }

    public void getMyBalance() {
        setProgress(true);
        getAccountData();
    }

    public void showSeed(String input) {
        decryptSeed(input);
        setSeed(userManager.getSeed());
    }

    public void decryptSeedWithoutShow(String input) {
        String seed = "";
        this.user = userManager.getCurrentUser();
        if (user!=null && user.getEncryptedSeed()!=null){
            try {
                AESCrypt aes = new AESCrypt(input);
                seed = aes.decrypt(user.getEncryptedSeed());
                this.seed = seed;
            } catch (Exception e) {
                getUiEvents().showToast(getResourceProvider().getString(R.string.err_seed_decypher));
                insertPassSeedDialog.accept(new DialogEvent());
            }
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isGettingAddresses() {
        return gettingAddresses;
    }

    public void setGettingAddresses(boolean gettingAddresses) {
        this.gettingAddresses = gettingAddresses;
    }
    @Bindable
    public String getProgressText() {
        return progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
        notifyPropertyChanged(BR.progressText);
    }

    public int getAskAddressTimes() {
        return askAddressTimes;
    }

    public int getAddressQuantity() {
        return addressQuantity;
    }
}
