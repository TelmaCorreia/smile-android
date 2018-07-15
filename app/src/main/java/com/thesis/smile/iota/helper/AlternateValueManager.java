package com.thesis.smile.iota.helper;

import android.content.Context;
import android.preference.PreferenceManager;

import com.thesis.smile.Constants;

import org.knowm.xchange.currency.Currency;

import jota.utils.IotaUnits;

public class AlternateValueManager {

    private final Context context;

    public AlternateValueManager(Context context) {
        this.context = context;
    }

    public float convert(long iotaAmount, Currency currency) {
        Currency baseCurrency = new Currency(Constants.IOTA_CURRENCY);
        AlternateValueCalculator calculator = new AlternateValueCalculator(baseCurrency,
                new ExchangeRateStorage(PreferenceManager.getDefaultSharedPreferences(context)));

        // convert the iota to mega iota assuming that iota will be traded in mega iotas
        double walletBalanceGigaIota = jota.utils.IotaUnitConverter.convertUnits(iotaAmount, IotaUnits.IOTA, IotaUnits.MEGA_IOTA);
        return calculator.calculateValue((float) walletBalanceGigaIota, currency);
    }

    public void updateExchangeRatesAsync(boolean updateSelective) {
        ExchangeRateStorage storage = new ExchangeRateStorage(PreferenceManager.getDefaultSharedPreferences(context));

        ExchangeRateUpdateTask exchangeRateUpdateTask = new ExchangeRateUpdateTask(context, new Currency(Constants.IOTA_CURRENCY),
                storage);
        exchangeRateUpdateTask.startNewRequestTask(updateSelective);
    }
}
