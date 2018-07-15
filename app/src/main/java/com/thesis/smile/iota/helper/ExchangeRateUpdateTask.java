package com.thesis.smile.iota.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.thesis.smile.Constants;

import org.knowm.xchange.currency.Currency;

public class ExchangeRateUpdateTask {

    private final Context context;
    private final Currency baseCurrency;
    private final ExchangeRateStorage exchangeRateProvider;

    public ExchangeRateUpdateTask(Context context, Currency baseCurrency, ExchangeRateStorage exchangeRateProvider) {
        this.context = context;
        this.baseCurrency = baseCurrency;
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public void startNewRequestTask(boolean updateSelective) {
        ExchangeRateUpdateTaskHandler rt = new ExchangeRateUpdateTaskHandler(baseCurrency,
                new Currency(Constants.EUR_CURRENCY), updateSelective);

        rt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, exchangeRateProvider);
    }
}
