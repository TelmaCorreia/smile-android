package com.thesis.smile.presentation.main.historical;

import android.util.Log;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHistoricalBinding;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.iota.responses.GetNewAddressResponse;
import com.thesis.smile.iota.responses.SendTransferResponse;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

public class HistoricalFragment extends BaseFragment<FragmentHistoricalBinding, HistoricalViewModel> {

    private final String TAG = HistoricalFragment.class.getCanonicalName();

    public static HistoricalFragment newInstance() {
        return new HistoricalFragment();
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_historical;
    }

    @Override
    protected Class<HistoricalViewModel> viewModelClass() {
        return HistoricalViewModel.class;
    }

    @Override
    protected void initViews(FragmentHistoricalBinding binding) {
        //initToolbar(binding.actionBar.appBar, binding.actionBar.toolbar, false, getResources().getString(R.string.historical_title));
    }

    @Subscribe
    public void onEvent(GetNewAddressResponse getNewAddressResponse) {
        //attach new
        getViewModel().attachNewAddress(getNewAddressResponse.getAddresses().get(0));
    }

    @Subscribe
    public void onEvent(SendTransferResponse str) {
        if (Arrays.asList(str.getSuccessfully()).contains(true))
            getViewModel().getAccountData();
    }

    @Subscribe
    public void onEvent(GetAccountDataResponse gad) {
        Log.d(TAG, "Account data response!");
    }

    @Subscribe
    public void onEvent(NetworkError error) {
        switch (error.getErrorType()) {
            case ACCESS_ERROR:
                Log.d(TAG, "Access error!");
                break;
            case REMOTE_NODE_ERROR:
                Log.d(TAG, "Remote node error!");
                break;
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

}
