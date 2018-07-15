package com.thesis.smile.presentation.iota_settings;

import android.content.Context;
import android.content.Intent;

import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityIotaSettingsBinding;
import com.thesis.smile.iota.helper.AlternateValueManager;
import com.thesis.smile.iota.helper.AlternateValueUtils;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.iota.responses.GetNewAddressResponse;
import com.thesis.smile.iota.responses.ReplayBundleResponse;
import com.thesis.smile.iota.responses.SendTransferResponse;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.iota.service.PaymentService;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.views.CustomInputDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.knowm.xchange.currency.Currency;

import java.util.Arrays;

import jota.utils.IotaUnitConverter;

public class IotaSettingsActivity extends BaseToolbarActivity<ActivityIotaSettingsBinding, IotaSettingsViewModel> {

    private CustomInputDialog showSeedDialog;
    private AlternateValueManager alternateValueManager;
    TemporalIotaViewPagerAdapter pagerAdapter;

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
        alternateValueManager = new AlternateValueManager(this);
        pagerAdapter = new TemporalIotaViewPagerAdapter(getSupportFragmentManager(), getResourceProvider());
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewpager);

        secureSeedDialog();
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeShowSeedDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::showSeedDialog);

        getViewModel().observeStartPaymentServiceEvent()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    startService(new Intent(this, PaymentService.class));
                });

       /* getViewModel().observeInsertPassSeedDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::secureSeedDialog);*/

    }
    @Subscribe
    public void onEvent(GetNewAddressResponse getNewAddressResponse) {
        //attach new
        getViewModel().saveAddress(getNewAddressResponse.getAddresses().get(0));
        getViewModel().attachNewAddress(getNewAddressResponse.getAddresses().get(0));
    }


    @Subscribe
    public void onEvent(SendTransferResponse str) {
        if (Arrays.asList(str.getSuccessfully()).contains(true)){
            getViewModel().getAccountData();
        }
    }

    @Subscribe
    public void onEvent(GetAccountDataResponse gad) {
        alternateValueManager.updateExchangeRatesAsync(false);
        getViewModel().message("Account data response!");

        String balanceIota = IotaUnitConverter.convertRawIotaAmountToDisplayText(gad.getBalance(), false);
        float euroBalance = updateAlternateBalance(gad.getBalance());
        String balanceEuro = AlternateValueUtils.formatAlternateBalanceText(euroBalance, new Currency(Constants.EUR_CURRENCY));

        getViewModel().setBalanceIota(balanceIota);
        getViewModel().setBalanceEuro(balanceEuro);
        getViewModel().setLoading(false);

    }

    @Subscribe
    public void onEvent(ReplayBundleResponse rbr) {
        getViewModel().message("Replay bundle response!");
        if (Arrays.asList(rbr.getSuccessfully()).contains(true)){
            getViewModel().getAccountData();
        }
    }

    @Subscribe
    public void onEvent(NetworkError error) {
        switch (error.getErrorType()) {
            case ACCESS_ERROR:
                getViewModel().message("Access error!");
                getViewModel().setLoading(false);
                break;
            case REMOTE_NODE_ERROR:
                getViewModel().message("Remote node error!");
                getViewModel().setLoading(false);
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
        showSeedDialog = new CustomInputDialog(this);
        showSeedDialog.setTitle(R.string.dialog_show_seed_title);
        showSeedDialog.setMessage(R.string.dialog_show_seed_description);
        showSeedDialog.setPrompt(R.string.prompt_pass);
        showSeedDialog.setOkButtonText(R.string.button_ok);
        showSeedDialog.setCloseButtonText(R.string.button_cancel);
        showSeedDialog.setDismissible(true);
        showSeedDialog.setOnOkClickListener(() -> {
            getViewModel().decryptSeed(showSeedDialog.getInput());
            getViewModel().getMyBalance();
            showSeedDialog.dismiss();
        });
        showSeedDialog.setOnCloseClickListener(() ->{showSeedDialog.dismiss();});
        showSeedDialog.show();
    }

    private float updateAlternateBalance(long walletBalanceIota) {
        Currency alternateCurrency = new Currency("EUR");
        return alternateValueManager.convert(walletBalanceIota, alternateCurrency);
    }

}
