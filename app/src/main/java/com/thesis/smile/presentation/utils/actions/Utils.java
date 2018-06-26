package com.thesis.smile.presentation.utils.actions;

import android.content.Context;
import android.text.TextUtils;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class Utils {

    public static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        return !TextUtils.isEmpty(password) && password.matches(pattern);
    }

    public static String normalizeUrl(String url) {
        if (url == null) return null;
        String PREFIX = "http:";
        return url.startsWith(PREFIX) ? url : PREFIX + url;
    }

    //On the play store it only exists the production package,
    // because of this, if we try to test this on DEV or QA
    // there wouldn't be a result,
    // so here we remove the .dev and .qa added on the build process for this flavors
    public static String getProductionPackageName(Context context){
        String devSuffix = ".dev";
        String qaSuffix = ".qa";
        String packageName = context.getPackageName();

        if(packageName.endsWith(devSuffix)){
            packageName = packageName.replace(devSuffix, "");
        }else if (packageName.endsWith(qaSuffix)) {
            packageName = packageName.replace(qaSuffix, "");
        }

        return packageName;
    }

    public static int createNewID() {
        Date now = new Date();
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.UK).format(now));
    }
}
