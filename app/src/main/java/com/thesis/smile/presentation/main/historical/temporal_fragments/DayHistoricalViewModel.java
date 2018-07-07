package com.thesis.smile.presentation.main.historical.temporal_fragments;

import android.databinding.Bindable;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.managers.HistoricalManager;
import com.thesis.smile.domain.models.HistoricalData;
import com.thesis.smile.domain.models.HistoricalDataPoint;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DayHistoricalViewModel extends BaseViewModel {

    private HistoricalManager historicalManager;
    private List<HistoricalData> historicalDataList;
    private HistoricalData currentData;
    private HistoricalDataPoint currentDay;
    private LocalDate currentDate = LocalDate.now();

    private PublishRelay<Event> dataReceived = PublishRelay.create();
    private boolean isLoading = false;

    @Inject
    public DayHistoricalViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider,
                                  UiEvents uiEvents, HistoricalManager historicalManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.historicalManager = historicalManager;
        getHistoricalDataFromServer();
    }

    @Bindable
    public String getTotalEnergyConsumption(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getTotalConsumption());
        }
        return null;
    }

    @Bindable
    public String getTotalEnergyProduction(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getTotalProduction());
        }
        return null;
    }

    @Bindable
    public String getTotalEnergySurplus(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergySurplus());
        }
        return null;
    }

    @Bindable
    public String getEnergySurplusSoldNeighbors(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergySurplusNeighbours());
        }
        return null;
    }

    @Bindable
    public String getEnergySurplusNotUsed(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergySurplusNotUsed());
        }
        return null;
    }

    @Bindable
    public String getTotalAutoConsumption(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergyAutoConsumptionTotal());
        }
        return null;
    }

    @Bindable
    public String getAutoConsumptionBattery(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergyAutoConsumptionBattery());
        }
        return null;
    }

    @Bindable
    public String getAutoConsumptionPanels(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergyAutoConsumptionPanels());
        }
        return null;
    }

    @Bindable
    public String getTotalEnergyBought(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergyBought());
        }
        return null;
    }

    @Bindable
    public String getEnergyBoughtNeighbors(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergyBoughtNeighbours());
        }
        return null;
    }

    @Bindable
    public String getEnergyBoughtEem(){
        if (currentDay!=null){
            return String.format("%.2f", currentDay.getEnergyBoughtEem());
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
    public String getLabel4(){
        if (currentData!=null && currentData.getDataPoints()!=null){
            return currentData.getDataPoints().get(4).getTitle();
        }
        return null;
    }

    @Bindable
    public String getLabel5(){
        if (currentData!=null && currentData.getDataPoints()!=null){
            return currentData.getDataPoints().get(5).getTitle();
        }
        return null;
    }

    @Bindable
    public String getLabel6(){
        if (currentData!=null && currentData.getDataPoints()!=null){
            return currentData.getDataPoints().get(6).getTitle();
        }
        return null;
    }

    @Bindable
    public boolean isProgress(){
        return isLoading;
    }

    @Bindable
    public  boolean isAlertWastedEnergy(){
        if (currentDay!=null) {
            return currentDay.getWastedEnergy()>0?true:false;
        }
        return false;
    }

    @Bindable
    public  boolean getShowEnergySoldDetails(){
        if (currentDay!=null) {
            return currentDay.getEnergySurplusNeighbours()>0?true:false;
        }
        return false;
    }



    public void setLoading(boolean loading){
        this.isLoading = loading;
    }

    public void getHistoricalDataFromServer() {
        setLoading(true);
        historicalManager.getDailyHistoricalData(currentDate)
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onHistoricalDataReceived, this::onError);
    }

    public void onPreviousClick(){
        setLoading(true);
        currentDate = currentDate.minusWeeks(1);
        historicalManager.getDailyHistoricalData(currentDate)
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onHistoricalDataReceived, this::onError);
    }

    public void onNextClick(){
        setLoading(true);
        currentDate = currentDate.plusWeeks(1);
        historicalManager.getDailyHistoricalData(currentDate)
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onHistoricalDataReceived, this::onError);
    }

    private void onHistoricalDataReceived(List<HistoricalData> data) {
        this.historicalDataList = data;
        if(data !=null && data.size()>0){
            this.currentData =data.get(0);
            this.currentDay=data.get(0).getDataPoints().get(0);
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

    public List<HistoricalData> getHistoricalDataList() {
        return historicalDataList;
    }

    public HistoricalData getCurrentData() {
        return currentData;
    }

    public HistoricalDataPoint getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(HistoricalDataPoint dp) {
        this.currentDay = dp;
        notifyChange();
    }

}
