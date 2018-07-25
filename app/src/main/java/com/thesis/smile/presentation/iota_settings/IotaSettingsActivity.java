package com.thesis.smile.presentation.iota_settings;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.thesis.smile.BuildConfig;
import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityIotaSettingsBinding;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.iota.helper.AlternateValueManager;
import com.thesis.smile.iota.helper.AlternateValueUtils;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.iota.responses.GetNewAddressResponse;
import com.thesis.smile.iota.responses.ReplayBundleResponse;
import com.thesis.smile.iota.responses.SendTransferResponse;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.views.CustomInputDialog;
import com.thesis.smile.presentation.utils.views.SeedPasswordDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.knowm.xchange.currency.Currency;

import java.util.Arrays;
import java.util.List;

import jota.utils.IotaUnitConverter;

public class IotaSettingsActivity extends BaseToolbarActivity<ActivityIotaSettingsBinding, IotaSettingsViewModel> {

    private CustomInputDialog showSeedDialog;
    private CustomInputDialog insertPasswordDialog;
    TemporalIotaViewPagerAdapter pagerAdapter;
    private AlternateValueManager alternateValueManager;
    private  int transactionsSize=0;
    private int transactionIndex=0;

    public static void launch(Context context) {
        Intent intent = new Intent(context, IotaSettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_iota_settings;
    }

    @Override
    protected Class<IotaSettingsViewModel> viewModelClass() {
        return IotaSettingsViewModel.class;
    }

    @Override
    protected void initViews(ActivityIotaSettingsBinding binding) {
        initToolbar(binding.actionBar.toolbar, true,  getResources().getString(R.string.iota_settings_title));
        pagerAdapter = new TemporalIotaViewPagerAdapter(getSupportFragmentManager(), getResourceProvider());
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewpager);
        this.alternateValueManager = new AlternateValueManager(this);
        secureSeedDialog();
    }



    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeShowSeedDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::showSeedDialog);

        getViewModel().observeInsertPasswordSeedDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event->{insertPasswordDialog.dismiss(); secureSeedDialog();});

        getViewModel().observeStartPaymentServiceEvent()
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::sendTransfers);

    }


    @Subscribe
    public void onEvent(GetNewAddressResponse getNewAddressResponse) {
        //attach new
        getViewModel().attachNewAddress(getNewAddressResponse.getAddresses().get(0));
    }


    @Subscribe
    public void onEvent(SendTransferResponse str) {
        if (transactionsSize==0 && !getViewModel().isGettingAddresses()){
            getViewModel().setScreenBlocked(false);
        }else{
            getViewModel().updateState(transactionIndex);
        }
        Log.d("Transfer", "Response!! transactions size: " +transactionsSize);
        Log.d("Transfer", "Response!! transactions index: " +transactionsSize);
        transactionIndex++;
        transactionsSize--;
        if (Arrays.asList(str.getSuccessfully()).contains(true)){
            if(BuildConfig.DEBUG) {
                getViewModel().message("Send transfer response!");
            }
        }
    }

    @Subscribe
    public void onEvent(GetAccountDataResponse gad) {
        if(BuildConfig.DEBUG) {
            getViewModel().message("Account data response!");
        }
        onAccountDataResponse(gad);
    }



    @Subscribe
    public void onEvent(ReplayBundleResponse rbr) {
        if(BuildConfig.DEBUG) {
            getViewModel().message("Replay bundle response!");
        }
        if (Arrays.asList(rbr.getSuccessfully()).contains(true)){
            getViewModel().getAccountData();
        }
    }

    @Subscribe
    public void onEvent(NetworkError error) {
        transactionIndex++;
        transactionsSize--;
        Log.d("Transfer", "Error!! transactions size: " +transactionsSize);
        Log.d("Transfer", "Error!! transactions index: " +transactionsSize);
        switch (error.getErrorType()) {
            case ACCESS_ERROR:
                getViewModel().message(getResources().getString(R.string.err_api_iota));
                getViewModel().setProgress(false);
                getViewModel().setScreenBlocked(false);
                break;
            case REMOTE_NODE_ERROR:
                getViewModel().message(getResources().getString(R.string.err_api_iota));
                getViewModel().setProgress(false);
                getViewModel().setScreenBlocked(false);
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void showSeedDialog(DialogEvent dialogEvent) {
        showSeedDialog = new CustomInputDialog(this);
        showSeedDialog.setTitle(R.string.dialog_show_seed_title);
        showSeedDialog.setMessage(R.string.dialog_show_seed_description);
        showSeedDialog.setPrompt(R.string.prompt_pass);
        showSeedDialog.setOkButtonText(R.string.button_ok);
        showSeedDialog.setCloseButtonText(R.string.button_cancel);
        showSeedDialog.setDismissible(true);
        showSeedDialog.setOnOkClickListener(() -> {
            getViewModel().showSeed(showSeedDialog.getInput());
            showSeedDialog.dismiss();
        });
        showSeedDialog.setOnCloseClickListener(() ->{showSeedDialog.dismiss();});
        showSeedDialog.show();
    }

    private void secureSeedDialog() {
        insertPasswordDialog = new CustomInputDialog(this);
        insertPasswordDialog.setTitle(R.string.dialog_show_seed_title);
        insertPasswordDialog.setMessage(R.string.dialog_show_seed_description);
        insertPasswordDialog.setOkButtonText(R.string.button_ok);
        insertPasswordDialog.setCloseButtonText(R.string.button_back);
        insertPasswordDialog.setDismissible(false);
        insertPasswordDialog.setOnOkClickListener(() -> {
            getViewModel().decryptSeedWithoutShow(insertPasswordDialog.getInput());
            if (!getViewModel().getSeed().isEmpty()){
                getViewModel().getMyBalance();
                insertPasswordDialog.dismiss();}
        });
        insertPasswordDialog.setOnCloseClickListener(() ->{insertPasswordDialog.dismiss(); this.onBackPressed();});
        insertPasswordDialog.show();
    }

    private void sendTransfers(Event event) {
        getViewModel().setScreenBlocked(true);
        List<Transaction> transactionList= getViewModel().getTransactions();
        transactionsSize = transactionList.size();
        Log.d("Transfer", "transactions size: " +transactionsSize);
        for(Transaction t: transactionList){
            long iotaBalance = euroToIota((float) t.getTotal());
            String balanceIota = IotaUnitConverter.convertRawIotaAmountToDisplayText(iotaBalance, false);
            Log.d("Transfer", "iota: " +iotaBalance + " : " +balanceIota + "\naddress: " + t.getAddress());
            getViewModel().sendTransfer(t, String.valueOf(iotaBalance));
        }
    }
    private float iotaToEuro(long walletBalanceIota) {
        Currency alternateCurrency = new Currency(Constants.EUR_CURRENCY);
        return alternateValueManager.convert(walletBalanceIota, alternateCurrency);
    }

    private long euroToIota(float euro) {
        float baseValue = iotaToEuro(1000000);
        return (long) ((euro/baseValue)*1000);
    }

    public void onAccountDataResponse(GetAccountDataResponse gad) {
        alternateValueManager.updateExchangeRatesAsync(false);
        String balanceIota = IotaUnitConverter.convertRawIotaAmountToDisplayText(gad.getBalance(), false);
        float euroBalance = iotaToEuro(gad.getBalance());
        String balanceEuro = AlternateValueUtils.formatAlternateBalanceText(euroBalance, new Currency(Constants.EUR_CURRENCY));
        getViewModel().setBalanceIota(balanceIota);
        getViewModel().setBalanceEuro(balanceEuro);
        getViewModel().setProgress(false);
    }


}
