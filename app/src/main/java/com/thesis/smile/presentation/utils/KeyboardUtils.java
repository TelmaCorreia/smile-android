package com.thesis.smile.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {
    public static void showKeyboard(Activity activity) {
        showKeyboard(activity.getCurrentFocus());
    }

    public static void hideKeyboard(Activity activity) {
        hideKeyboard(activity.getCurrentFocus());
    }

    public static void showKeyboard(Fragment fragment){
        if(fragment.getActivity() != null)
            showKeyboard(fragment.getActivity().getCurrentFocus());
    }

    public static void hideKeyboard(Fragment fragment){
        if(fragment.getActivity() != null)
            hideKeyboard(fragment.getActivity().getCurrentFocus());
    }

    public static void showKeyboard(View view) {
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    public static void hideKeyboard(View view) {
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
