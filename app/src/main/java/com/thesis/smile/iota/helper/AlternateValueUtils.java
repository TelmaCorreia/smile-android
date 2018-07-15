package com.thesis.smile.iota.helper;

import org.knowm.xchange.currency.Currency;

import java.text.DecimalFormat;

public class AlternateValueUtils {

    public static String formatAlternateBalanceText(float value, Currency currency) {
        return new DecimalFormat("##0.00").format(value) + " " + getSymbol(currency);
    }

    private static String getSymbol(Currency currency) {
        if (currency.equals(Currency.BTC))
            return "Ƀ";
        else if (currency.equals(Currency.USD))
            return "$";
        else if (currency.equals(Currency.EUR))
            return "€";
        else if (currency.equals(Currency.CNY))
            return "¥";

        // should never happen:
        return "";
    }
}
