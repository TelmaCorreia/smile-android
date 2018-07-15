package com.thesis.smile.iota.helper;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.okcoin.OkCoinExchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateUpdater {

    private final CurrencyPair baseCurrencyBtcPair;

    private final ExchangeRateStorage storage;
    private final Map<CurrencyPair, Exchange> exchanges;

    public ExchangeRateUpdater(Currency baseCurrency, ExchangeRateStorage priceStorage) {
        this.storage = priceStorage;
        this.baseCurrencyBtcPair = new CurrencyPair(baseCurrency, Currency.BTC);

        exchanges = new HashMap<>();
        exchanges.put(baseCurrencyBtcPair, ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName()));
        exchanges.put(CurrencyPair.BTC_USD, ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName()));
        exchanges.put(CurrencyPair.BTC_EUR, ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName()));
        exchanges.put(CurrencyPair.BTC_CNY, ExchangeFactory.INSTANCE.createExchange(OkCoinExchange.class.getName()));

        // add more currencies/exchange pairs (btc/fiat) here
    }

    public void update() {
        for (CurrencyPair currencyPair : exchanges.keySet()) {
            update(currencyPair);
        }
    }

    private void update(CurrencyPair currencyPair) {
        try {
            if (exchanges.containsKey(currencyPair)) {
                BigDecimal price = exchanges.get(currencyPair)
                        .getMarketDataService()
                        .getTicker(currencyPair)
                        .getLast();

                storage.setExchangeRate(currencyPair, price.floatValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateResourceEfficient(CurrencyPair preferredCurrencyPair) {

        // update base/btc
        update(baseCurrencyBtcPair);
        // update btc/preferred currency
        if (!baseCurrencyBtcPair.equals(preferredCurrencyPair))
            update(preferredCurrencyPair);
    }
}
