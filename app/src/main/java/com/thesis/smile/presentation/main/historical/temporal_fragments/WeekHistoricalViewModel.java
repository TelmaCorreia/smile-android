package com.thesis.smile.presentation.main.historical.temporal_fragments;

import android.databinding.Bindable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.managers.HistoricalManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.HistoricalData;
import com.thesis.smile.domain.models.HistoricalDataPoint;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class WeekHistoricalViewModel extends BaseViewModel {

    private HistoricalManager historicalManager;
    private List<HistoricalData> historicalDataList;
    private HistoricalData currentData;
    private HistoricalDataPoint currentWeek;
    private LocalDate currentDate = LocalDate.now();
    private UserManager userManager;

    private PublishRelay<Event> dataReceived = PublishRelay.create();
    private PublishRelay<Event> wasteDetailsObservable = PublishRelay.create();
    private PublishRelay<Event> soldDetailsObservable = PublishRelay.create();
    private boolean isLoading = false;

    @Inject
    public WeekHistoricalViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                   UiEvents uiEvents, HistoricalManager historicalManager, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.historicalManager = historicalManager;
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Historical:week")
                .putContentType("Section Historical")
                .putContentId("historical_week")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail())
                .putCustomAttribute("hour", LocalTime.now().getHour()));
        getHistoricalDataFromServer();
    }

    @Bindable
    public String getTotalEnergyConsumption(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getTotalConsumption());
        }
        return null;
    }

    @Bindable
    public String getTotalEnergyProduction(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getTotalProduction());
        }
        return null;
    }

    @Bindable
    public String getTotalEnergySurplus(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergySurplusNeighbours()+ currentWeek.getEnergySurplusNotUsed());
        }
        return null;
    }

    @Bindable
    public String getEnergySurplusSoldNeighbors(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergySurplusNeighbours());
        }
        return null;
    }

    @Bindable
    public String getEnergySurplusNotUsed(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergySurplusNotUsed());
        }
        return null;
    }

    @Bindable
    public String getTotalAutoConsumption(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergyAutoConsumptionTotal());
        }
        return null;
    }

    @Bindable
    public String getAutoConsumptionBattery(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergyAutoConsumptionBattery());
        }
        return null;
    }

    @Bindable
    public String getAutoConsumptionPanels(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergyAutoConsumptionPanels());
        }
        return null;
    }

    @Bindable
    public String getTotalEnergyBought(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergyBought());
        }
        return null;
    }

    @Bindable
    public String getEnergyBoughtNeighbors(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergyBoughtNeighbours());
        }
        return null;
    }

    @Bindable
    public String getEnergyBoughtEem(){
        if (currentWeek!=null){
            return String.format("%.2f", currentWeek.getEnergyBoughtEem());
        }
        return null;
    }

    @Bindable
    public String getTitle(){
        if (currentData!=null){
            return currentData.getTimeDescription();
        }
        return null;
    }

    @Bindable
    public String getLabel0(){
        if (currentData!=null && currentData.getDataPoints()!=null){
            return currentData.getDataPoints().get(0).getTitle();
        }
        return null;
    }

    @Bindable
    public String getLabel1(){
        if (currentData!=null && currentData.getDataPoints()!=null){
            return currentData.getDataPoints().get(1).getTitle();
        }
        return null;
    }

    @Bindable
    public String getLabel2(){
        if (currentData!=null && currentData.getDataPoints()!=null){
            return currentData.getDataPoints().get(2).getTitle();
        }
        return null;
    }

    @Bindable
    public String getLabel3(){
        if (currentData!=null && currentData.getDataPoints()!=null){
            return currentData.getDataPoints().get(3).getTitle();
        }
        return null;
    }


    @Bindable
    public boolean isProgress(){
        return isLoading;
    }

    @Bindable
    public  boolean isAlertWastedEnergy(){
        if (currentWeek!=null) {
            return currentWeek.getEnergySurplusNotUsed()>0?true:false;
        }
        return false;
    }

    @Bindable
    public  boolean getShowEnergySoldDetails(){
        if (currentWeek!=null) {
            return currentWeek.getEnergySurplusNeighbours()>0?true:false;
        }
        return false;
    }

    public void setLoading(boolean loading){
        this.isLoading = loading;
    }

    public void getHistoricalDataFromServer() {
        setLoading(true);
        historicalManager.getWeeklyHistoricalData(currentDate)
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onHistoricalDataReceived, this::onError);
    }

    public void onPreviousClick(){
        setLoading(true);
        currentDate = currentDate.minusWeeks(1);
        historicalManager.getWeeklyHistoricalData(currentDate)
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onHistoricalDataReceived, this::onError);
    }

    public void onNextClick(){
        setLoading(true);
        currentDate = currentDate.plusWeeks(1);
        historicalManager.getWeeklyHistoricalData(currentDate)
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onHistoricalDataReceived, this::onError);
    }

    public void onEnergySoldDetailsClick(){
        soldDetailsObservable.accept(new Event());
    }

    public void onEnergyWastedDetailsClick(){
        wasteDetailsObservable.accept(new Event());
    }


    private void onHistoricalDataReceived(List<HistoricalData> data) {
        this.historicalDataList = data;
        if(data !=null && data.size()>0){
            this.currentData =data.get(0);
            this.currentWeek=data.get(0).getDataPoints().get(0);
            notifyChange();
        }
        this.setLoading(false);
        dataReceived.accept(new Event());
    }

    @Override
    public void onError(Throwable e){
        setLoading(false);
        super.onError(e);
    }

    public Observable<Event> observeHistoricalData() {
        return dataReceived;
    }
    public Observable<Event> observeWasteDetails() {
        return wasteDetailsObservable;
    }
    public Observable<Event> observeSoldDetails() {
        return soldDetailsObservable;
    }

    public List<HistoricalData> getHistoricalDataList() {
        return historicalDataList;
    }

    public HistoricalData getCurrentData() {
        return currentData;
    }

    public HistoricalDataPoint getCurrentDay() {
        return currentWeek;
    }

    public void setCurrentDay(HistoricalDataPoint dp) {
        this.currentWeek = dp;
        notifyChange();
    }

}
