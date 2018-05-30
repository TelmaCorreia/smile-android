package com.thesis.smile.presentation.main.historical;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.domain.managers.IotaManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.iota.IotaTaskManager;
import com.thesis.smile.iota.handler.GetAccountDataRequestHandler;
import com.thesis.smile.iota.requests.GetAccountDataRequest;
import com.thesis.smile.iota.requests.GetNewAddressRequest;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HistoricalViewModel extends BaseViewModel {

    List<Address> addresses;
    private IotaManager iotaManager;

    @Inject
    public HistoricalViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, UserManager userManager, IotaManager iotaManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.iotaManager = iotaManager;
        this.addresses = new ArrayList<>();
        doRequestTest();
    }

    private void doRequestTest() {
        iotaManager.generateNewAddress();
    }


}
