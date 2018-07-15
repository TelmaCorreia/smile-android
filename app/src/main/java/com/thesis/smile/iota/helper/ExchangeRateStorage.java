package com.thesis.smile.iota.helper;

import android.content.SharedPreferences;

import com.thesis.smile.Constants;

import org.knowm.xchange.currency.CurrencyPair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExchangeRateStorage implements IExchangeRateProvider{

    private static final String TIMESTAMP = "timestamp";
    private final SharedPreferences sharedPreferences;

    public ExchangeRateStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public synchronized void setExchangeRate(CurrencyPair currencyPair, float rate) {
        String sharedPrefKey = getKey(currencyPair);

        sharedPreferences.edit()
                .putFloat(sharedPrefKey, rate)
                .putString(getTimeStampKey(currencyPair), createTimeStamp())
                .apply();
    }

    @Override
    public synchronized float getExchangeRate(CurrencyPair currencyPair){
        float NOT_AVAILABLE = -1;
        float exchangeRate = sharedPreferences.getFloat(getKey(currencyPair), NOT_AVAILABLE);

        return exchangeRate;
    }

    public synchronized String getTimeStamp(CurrencyPair currencyPair) {
        return sharedPreferences.getString(getTimeStampKey(currencyPair), "-");
    }

    private String getKey(CurrencyPair currencyPair) {
        return Constants.PRICE_STORAGE_PREFIX + "_" + currencyPair.toString();
    }

    private String getTimeStampKey(CurrencyPair currencyPair) {
        return getKey(currencyPair) + "_" + TIMESTAMP;
    }

    private String createTimeStamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        return df.format(new Date());
    }

}
