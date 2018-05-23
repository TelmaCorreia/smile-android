package com.thesis.smile.presentation.utils.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.thesis.smile.databinding.DialogCustomBinding;


public class CustomInputDialog extends Dialog {


    public interface OnCloseClickListener{
        void onClose();
    }

    public interface OnOkClickListener {
        void onOK();
    }


    private DialogCustomBinding binding;
    private boolean isDismissible;
    private OnCloseClickListener closeListener;

    public CustomInputDialog(@NonNull Activity activity){
        super(activity);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //binding = DialogInputCustomBinding(inflater, null, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        isDismissible = true;
        setContentView(binding.getRoot());
        setOnDismissListener(dialogInterface -> {
            if(closeListener != null){
                closeListener.onClose();
            }
        });
    }

    public void setTitle(@StringRes int title){
        setTitle(getContext().getString(title));
    }

    public void setTitle(String title){
        binding.title.setText(title);
    }

    public void setMessage(@StringRes int message){
        setMessage(getContext().getString(message));
    }


    public void setMessage(String message){
        binding.message.setText(message);
    }

    public void setOkButtonText(@StringRes int okButtonText){
        setOkButtonText(getContext().getString(okButtonText));
    }

    public String getInput(){
       return "";// return binding.;
    }

    public void setOkButtonText(String okButtonText){
        binding.okButton.setText(okButtonText);
    }

    public void setCloseButtonText(@StringRes int closeButtonText){
        setCloseButtonText(getContext().getString(closeButtonText));
    }

    public void setCloseButtonText(String closeButtonText){
        binding.close.setText(closeButtonText);
    }


    public void setOnOkClickListener(OnOkClickListener listener){
        binding.okButton.setOnClickListener(view -> listener.onOK());
    }

    public void setOnCloseClickListener(OnCloseClickListener listener){
        binding.close.setOnClickListener(view -> listener.onClose());
    }

    public void setDismissible(boolean isDismissible){
        this.isDismissible = isDismissible;
        super.setCancelable(isDismissible);
        binding.close.setVisibility(isDismissible ? View.VISIBLE : View.GONE);
    }
}
