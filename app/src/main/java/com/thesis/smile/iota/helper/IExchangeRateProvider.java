package com.thesis.smile.iota.helper;

import org.knowm.xchange.currency.CurrencyPair;

interface IExchangeRateProvider {

    float getExchangeRate(CurrencyPair currencyPair);
}
