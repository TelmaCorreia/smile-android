package com.thesis.smile.presentation.utils.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.thesis.smile.databinding.DialogInputCustomBinding;
import com.thesis.smile.databinding.DialogSeedPasswordBinding;


public class SeedPasswordDialog extends Dialog {



    public interface OnOkClickListener {
        void onOK();
    }


    private DialogSeedPasswordBinding binding;
    private boolean isDismissible;

    public SeedPasswordDialog(@NonNull Activity activity){
        super(activity);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        binding = DialogSeedPasswordBinding.inflate(inflater, null, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        isDismissible = true;
        setContentView(binding.getRoot());
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
       return binding.input.getText().toString();// return binding.;
    }

    public void setOkButtonText(String okButtonText){
        binding.okButton.setText(okButtonText);
    }

    public void setOnOkClickListener(OnOkClickListener listener){
        binding.okButton.setOnClickListener(view -> listener.onOK());
    }


    public void setDismissible(boolean isDismissible){
        this.isDismissible = isDismissible;
        super.setCancelable(isDismissible);
    }
}
