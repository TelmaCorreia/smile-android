package com.thesis.smile.presentation.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

public class SpanUtils {

    public static Spannable underlineString(String targetStr, String lookFor) {

        Spannable spanString = null;
        spanString = new SpannableString(targetStr);
        int fromIndex, startSpan, endSpan;

        fromIndex = 0;
        while (fromIndex < (targetStr.length() - 1)) {
            startSpan = targetStr.indexOf(lookFor, fromIndex);
            if (startSpan == -1)
                break;
            endSpan = startSpan + lookFor.length();
            spanString.setSpan(new UnderlineSpan(), startSpan,
                    endSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            fromIndex = endSpan;
        }
        return spanString;
    }
}
