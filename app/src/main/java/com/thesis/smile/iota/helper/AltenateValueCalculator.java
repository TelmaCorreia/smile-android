package com.thesis.smile.iota.helper;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

class AlternateValueCalculator {

    private final Currency baseCurrency; // currency of the altcoin (iota)
    private final IExchangeRateProvider exchangeRateProvider;

    public AlternateValueCalculator(Currency baseCurrency, IExchangeRateProvider exchangeRateProvider) {
        this.baseCurrency = baseCurrency;
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public float calculateValue(float baseAmount, Currency targetCurrency) {
        // convert the amount to btc first
        float btcAmount = calcBtcAmount(baseAmount);
        float targetValue;

        // if the target currency is btc we do nothing, otherwise we use the rate
        if (targetCurrency.equals(Currency.BTC)) {
            targetValue = btcAmount;
        } else {

            float fiatBtcPrice = exchangeRateProvider.getExchangeRate(new CurrencyPair(Currency.BTC, targetCurrency));
            targetValue = btcAmount * fiatBtcPrice;
        }

        return targetValue;
    }

    private float calcBtcAmount(float baseAmount){
        float lastBaseBtcPrice = getBaseBtcPrice();
        return lastBaseBtcPrice * baseAmount;
    }

    // overwrite this method to get a fixed exchange rate
    private float getBaseBtcPrice() {
        return exchangeRateProvider.getExchangeRate(new CurrencyPair(baseCurrency, Currency.BTC));
    }
}
