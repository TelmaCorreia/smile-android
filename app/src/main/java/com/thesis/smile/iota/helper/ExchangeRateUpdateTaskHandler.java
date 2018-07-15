package com.thesis.smile.iota.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.thesis.smile.BuildConfig;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.iota.responses.error.NetworkErrorType;

import org.greenrobot.eventbus.EventBus;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class ExchangeRateUpdateTaskHandler extends AsyncTask<ExchangeRateStorage, String, String> {

    private final Currency baseCurrency;
    private final Currency preferredCurrency;
    private final boolean updateSelective;
    private Boolean networkError = false;

    public ExchangeRateUpdateTaskHandler(Currency baseCurrency, Currency preferredCurrency, boolean updateSelective) {
        this.baseCurrency = baseCurrency;
        this.preferredCurrency = preferredCurrency;
        this.updateSelective = updateSelective;
    }

    @Override
    protected String doInBackground(ExchangeRateStorage... params) {
        try {
            ExchangeRateUpdater exchangeRateUpdater = new ExchangeRateUpdater(baseCurrency, params[0]);

            if (updateSelective) {
                exchangeRateUpdater.updateResourceEfficient(new CurrencyPair(Currency.BTC, preferredCurrency));

                if (BuildConfig.DEBUG)
                    Log.d(getClass().getName(), "Updating price selective");

            }else

            if (BuildConfig.DEBUG)
                Log.d(getClass().getName(), "Updating price");

            exchangeRateUpdater.update();

        } catch (Exception e) {
            networkError = true;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        EventBus bus = EventBus.getDefault();

        if (networkError) {
            NetworkError error = new NetworkError();
            error.setErrorType(NetworkErrorType.EXCHANGE_RATE_ERROR);
            bus.post(error);
        } else
            bus.post(new ExchangeRateUpdateCompleted());
    }

    public class ExchangeRateUpdateCompleted {
    }
}
