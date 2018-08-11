package com.thesis.smile.presentation.main.home.pager_fragments;

import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.exceptions.api.NoContentException;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.User;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.base.ViewModelFactory_Factory;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TransactionsHomeViewModel extends BaseViewModel {

    private CurrentEnergy currentEnergy;
    private PublishRelay<Event> dataReceived = PublishRelay.create();
    private List<String> legendList = new ArrayList();
    public PublishRelay<NavigationEvent> openHomeBoughtDetails = PublishRelay.create();
    public PublishRelay<NavigationEvent> openHomeSoldDetails = PublishRelay.create();
    private TransactionsManager transactionsManager;
    private UserManager userManager;

    @Inject
    public TransactionsHomeViewModel(ResourceProvider paramResourceProvider, SchedulerProvider paramSchedulerProvider, UiEvents paramUiEvents, UserManager paramUserManager, TransactionsManager paramTransactionsManager)
    {
        super(paramResourceProvider, paramSchedulerProvider, paramUiEvents);
        this.userManager = paramUserManager;
        this.transactionsManager = paramTransactionsManager;
        getCurrentEnergyFromServer();
    }

    private void onReceiveHomeData(CurrentEnergy paramCurrentEnergy)
    {
        this.currentEnergy = paramCurrentEnergy;
        notifyChange();
        this.dataReceived.accept(new Event());
    }

    public CurrentEnergy getCurrentEnergy()
    {
        return this.currentEnergy;
    }

    public void getCurrentEnergyFromServer() {

        transactionsManager.getHomeData()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onReceiveHomeData, this::onError);

    }

    @Bindable
    public int getEnergySoldVisible(){
        if(currentEnergy !=  null){
            return currentEnergy.getTotalSold() > 0 ? View.VISIBLE : View.GONE;
        }
        return View.GONE;
    }

    @Bindable
    public int getEnergyBoughtVisible(){
        if(currentEnergy !=  null){
            return currentEnergy.getTotalBought() > 0 ? View.VISIBLE : View.GONE;
        }
        return View.GONE;
    }

    @Bindable
    public int getEnergyBoughtInvisible(){
        if(getEnergyBoughtVisible()== View.GONE){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    @Bindable
    public int getUserTypeProsumer(){
        if(userManager.getCurrentUser()!=null && userManager.getCurrentUser().getType() !=null ){
            return userManager.getCurrentUser().getType().equals(getResourceProvider().getString(R.string.consumer))? View.GONE : View.VISIBLE;
        }
        return View.VISIBLE;

    }

    @Bindable
    public int getEnergySoldInvisible(){
        if(getEnergySoldVisible()== View.GONE && getUserTypeProsumer()!=View.GONE){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public List<String> getLegendList()
    {
        this.legendList.add("");
        this.legendList.add("2");
        this.legendList.add("");
        this.legendList.add("4");
        this.legendList.add("");
        this.legendList.add("6");
        this.legendList.add("");
        this.legendList.add("8");
        this.legendList.add("");
        this.legendList.add("10");
        this.legendList.add("");
        this.legendList.add("12");
        this.legendList.add("");
        this.legendList.add("14");
        this.legendList.add("");
        this.legendList.add("16");
        this.legendList.add("");
        this.legendList.add("18");
        this.legendList.add("");
        this.legendList.add("20");
        this.legendList.add("");
        this.legendList.add("22");
        this.legendList.add("");
        this.legendList.add("");
        return this.legendList;
    }

    @Bindable
    public String getTotalBought() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getTotalBought()) + " " + getResourceProvider().getString(R.string.coin);
        }
        return null;
    }

    @Bindable
    public String getTotalSold() {
        if(currentEnergy != null){
            return String.format("%.2f", currentEnergy.getTotalSold()) + " " + getResourceProvider().getString(R.string.coin);
        }
        return null;
    }

    public Observable<Event> observeData()
    {
        return this.dataReceived;
    }

    Observable<NavigationEvent> observeOpenHomeBoughtDetails()
    {
        return this.openHomeBoughtDetails;
    }

    Observable<NavigationEvent> observeOpenHomeSoldDetails()
    {
        return this.openHomeSoldDetails;
    }

    public void onEnergyBoughtDetailsClick()
    {
        Answers.getInstance().logContentView((ContentViewEvent)((ContentViewEvent)new ContentViewEvent().putContentName("Home:Check bought details").putContentType("Section Home").putContentId("home_bought_details").putCustomAttribute("email", this.userManager.getCurrentUser().getEmail())).putCustomAttribute("hour", Integer.valueOf(LocalTime.now().getHour())));
        this.openHomeBoughtDetails.accept(new NavigationEvent());
    }

    public void onEnergySoldDetailsClick()
    {
        Answers.getInstance().logContentView((ContentViewEvent)((ContentViewEvent)new ContentViewEvent().putContentName("Home:Check sold details").putContentType("Section Home").putContentId("home_sold_details").putCustomAttribute("email", this.userManager.getCurrentUser().getEmail())).putCustomAttribute("hour", Integer.valueOf(LocalTime.now().getHour())));
        this.openHomeSoldDetails.accept(new NavigationEvent());
    }

    @Override
    public void onError(Throwable paramThrowable)
    {
        if (!(paramThrowable instanceof NoContentException)) {
            getUiEvents().showToast(getResourceProvider().getString(R.string.no_data_msg));
        }
    }


}
