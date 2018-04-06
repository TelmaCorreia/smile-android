package com.thesis.smile.presentation.utils.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.thesis.smile.databinding.DialogCustomBinding;


public class CustomDialog extends Dialog {

    public interface OnCloseClickListener{
        void onClose();
    }

    public interface OnOkClickListener {
        void onOK();
    }

    private DialogCustomBinding binding;
    private boolean isDismissible;
    private OnCloseClickListener closeListener;

    public CustomDialog(@NonNull Activity activity){
        super(activity);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        binding = DialogCustomBinding.inflate(inflater, null, true);
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
        closeListener = listener;
        binding.root.setOnClickListener(view -> {
            if(isDismissible) {
                listener.onClose();
            }
        });
        binding.close.setOnClickListener(view -> {
            if(isDismissible) {
                listener.onClose();
            }
        });
    }

    public void setDismissible(boolean isDismissible){
        this.isDismissible = isDismissible;
        super.setCancelable(isDismissible);
        binding.close.setVisibility(isDismissible ? View.VISIBLE : View.GONE);
    }
}
