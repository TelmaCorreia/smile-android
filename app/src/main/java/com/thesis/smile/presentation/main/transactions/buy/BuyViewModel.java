package com.thesis.smile.presentation.main.transactions.buy;

import android.databinding.Bindable;
import android.databinding.ObservableList;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsSettingsManager;
import com.thesis.smile.domain.models.BuySettings;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class BuyViewModel extends BaseViewModel {

    private final ExclusiveObservableList<TimeInterval> timeIntervals;
    private final List<Neighbour> neighbours;
    private Map<String, Neighbour> neighboursToUpdate;
    private BuySettings buySettings;
    private BuySettings previousSettings;
    private TransactionsSettingsManager buySettingsManager;

    private Map<String, TimeInterval> timersToUpdate;
    private PublishRelay<OpenDialogEvent> alertDialog = PublishRelay.create();
    private PublishRelay<NavigationEvent> openPriceInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openTimerObservable = PublishRelay.create();
    private PublishRelay<Event> neighboursChanged = PublishRelay.create();
    private PublishRelay<Event> neighboursStateChanged = PublishRelay.create();
    private PublishRelay<Event> timeIntervalsStateChanged = PublishRelay.create();
    private PublishRelay<Event> radioChanged = PublishRelay.create();


    @Inject
    public BuyViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsSettingsManager buySettingsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.buySettingsManager = buySettingsManager;
        timeIntervals = new ExclusiveObservableList<>();
        neighbours = new ArrayList<>();
        neighboursToUpdate = new HashMap<>();
        timersToUpdate = new HashMap<>();
        getTimeIntervalsFromServer();
        getBuySettingsFromServer();
    }

    @Bindable
    public boolean isBuy() {
        if(buySettings!=null){
            return buySettings.isOn();
        }
        return false;
    }

    public void setBuy(boolean buy) {
        if(buySettings!=null) {
            this.buySettings.setOn(buy);
            if (buy && buySettings.isOn() && !previousSettings.isOn()){
                alertDialog.accept(new OpenDialogEvent());
            }
            notifyPropertyChanged(BR.buy);
            if (!buy) {
                notifyPropertyChanged(BR.saveVisible);
            }
        }
    }

    @Bindable
    public boolean isPlusPriceEditable() {
        if(buySettings!=null){
            return buySettings.isEemPlusPrice();
        }
        return false;
    }

    @Bindable
    public String getPlusPriceValue() {
        if(buySettings!=null && buySettings.getEemPlusPriceValue()>0 && buySettings.isEemPlusPrice()){
            return String.format("%.2f", buySettings.getEemPlusPriceValue());
        }
        return null;
    }

    public void setPlusPriceValue(String plusPriceValue) {

            if(buySettings!=null && buySettings.isEemPlusPrice() && !plusPriceValue.isEmpty()) {
            try{
                double value = Double.parseDouble(plusPriceValue.replace(',', '.'));
                buySettings.setEemPlusPriceValue(value);
                notifyPropertyChanged(BR.saveVisible);
            }catch (NumberFormatException e){
                getUiEvents().showToast(getResourceProvider().getString(R.string.alert_only_numbers));
            }

        }

    }

    public boolean isAllNeighboursSelected() {
        if(buySettings!=null){
            return buySettings.isAllNeighboursSelected();
        }
        return false;
    }

    public void setAllNeighboursSelected(boolean value) {
        if(buySettings!=null){
            buySettings.setAllNeighboursSelected(value);
            for (Neighbour n: neighbours){
                if (n.isSelectAll()){
                    n.setBlocked(value);
                }
                if(n.isBlocked()!=value && !n.isSelectAll()){
                    n.setBlocked(value);
                    addNeighbourToUpdate(n);
                }
            }
            neighboursStateChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
        }
    }

    public void setAllNeighboursToFalse(){
        if(buySettings!=null){
            buySettings.setAllNeighboursSelected(false);
            for (Neighbour n: neighbours){
                if (n.isSelectAll()){
                    n.setBlocked(false);
                    break;
                }
            }
            neighboursStateChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
        }
    }

    @Bindable
    public boolean isSaveVisible() {
        if(buySettings!=null) {
            return buySettingsChanged() || neighboursToUpdate.size()>0  || timersToUpdate.size()>0;
        }
        return false;
    }

    private boolean buySettingsChanged(){
        return  !(buySettings.isOn()== previousSettings.isOn()
                && buySettings.isAllNeighboursSelected()==previousSettings.isAllNeighboursSelected()
                && buySettings.isEemPrice()==previousSettings.isEemPrice()
                && buySettings.isEemPlusPrice()==previousSettings.isEemPlusPrice()
                && buySettings.getEemPlusPriceValue()==previousSettings.getEemPlusPriceValue()
                && buySettings.getEemPriceValue()==previousSettings.getEemPriceValue());
    }

    public void onEemPriceClick(){
        if(buySettings!=null){
            buySettings.setEemPrice(!buySettings.isEemPrice());
            buySettings.setEemPlusPrice(!buySettings.isEemPlusPrice());
            radioChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
            notifyPropertyChanged(BR.plusPriceEditable);
        }
    }

    public void onEemPlusPriceClick(){
        if(buySettings!=null){
            buySettings.setEemPrice(!buySettings.isEemPrice());
            buySettings.setEemPlusPrice(!buySettings.isEemPlusPrice());
            radioChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
            notifyPropertyChanged(BR.plusPriceEditable);
        }
    }

    public Map<String, Neighbour> getNeighboursToUpdate() {
        return neighboursToUpdate;
    }

    public void onPriceInfoClick(){
        openPriceInfoObservable.accept(new NavigationEvent());
    }

    public void onAddTimerClick(){
        openTimerObservable.accept(new NavigationEvent());
    }

    public ObservableList<TimeInterval> getTimeIntervals() {
        return this.timeIntervals;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public BuySettings getBuySettings() {
        return buySettings;
    }

    /**
     * TIME INTERVALS
     * */

    public void getTimeIntervalsFromServer(){
        Disposable disposableTimeIntervals = buySettingsManager.getTimeIntervalsBuy()
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onTimeIntervalsReceived, this::onError);

        addDisposable(disposableTimeIntervals);
    }

    private void onTimeIntervalsReceived(List<TimeInterval> timeIntervals) {
        this.timeIntervals.clear();
        this.timeIntervals.addAll(timeIntervals);
    }

    public void addTimeInterval(TimeInterval timeInterval, boolean update){
        Disposable disposable;
        if (!update){
            disposable = buySettingsManager.postTimeIntervalBuy(timeInterval)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTimeIntervalReceived, this::onError);
        }else{
            disposable = buySettingsManager.updateTimeInterval(timeInterval)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTimeIntervalUpdatedReceived, this::onError);
        }

        addDisposable(disposable);
    }

    private void onTimeIntervalReceived(TimeInterval timeInterval) {
        if (timeIntervals!=null){
            timeIntervals.add(timeInterval);
        }
    }

    private void onTimeIntervalUpdatedReceived(TimeInterval timeInterval) {
        for (TimeInterval t: timeIntervals){
            if(t.getId().equals(timeInterval.getId())){
                t.setFrom(timeInterval.getFrom());
                t.setActivated(timeInterval.isActivated());
                t.setTo(timeInterval.getTo());
                t.setWeekDays(timeInterval.getWeekDays());
                t.setWeekDaysString(timeInterval.getWeekDaysString());
            }
        }
        timersToUpdate.remove(timeInterval.getId());
        notifyPropertyChanged(BR.saveVisible);
        timeIntervalsStateChanged.accept(new Event());
    }

    public void removeTimerInterval(TimeInterval timeInterval) {
        Disposable disposable = buySettingsManager.deleteTimeInterval(timeInterval)
                .compose(loadingTransformCompletable())
                .compose(schedulersTransformCompletableIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onTimeIntervalRemoved, this::onError);
        addDisposable(disposable);
    }

    private void onTimeIntervalRemoved() {
        getTimeIntervalsFromServer();
    }

    public void addTimerIntervalToUpdate(TimeInterval timer) {
        if (timersToUpdate.containsKey(timer.getId())){
            timersToUpdate.remove(timer.getId());
        }else{
            timersToUpdate.put(timer.getId(), timer);
        }   notifyPropertyChanged(BR.saveVisible);
    }

    /**
     * NEIGHBOURS
     * **/

    public void getNeighboursFromServer(){
        Disposable disposableNeighbours = buySettingsManager.getNeighboursBuy(0, 20)
                .compose(schedulersTransformSingleIo())
                .subscribe(this::onNeighboursReceived, this::onError);
        addDisposable(disposableNeighbours);
    }

    private void onNeighboursReceived(List<Neighbour> neighbours) {
        this.neighbours.add(new Neighbour("0",getResourceProvider().getString(R.string.select_all), isAllNeighboursSelected(), true));
        this.neighbours.addAll(neighbours);
        neighboursChanged.accept(new Event());
    }

    public void addNeighbourToUpdate(Neighbour neighbour) {
        if(!neighbour.isBlocked() && isAllNeighboursSelected()){
            setAllNeighboursToFalse();
        }
        if (neighboursToUpdate.containsKey(neighbour.getId())){
            neighboursToUpdate.remove(neighbour.getId());
        }else{
            neighboursToUpdate.put(neighbour.getId(), neighbour);
        }
        notifyPropertyChanged(BR.saveVisible);
    }


    /**
     * SETTINGS
     * **/

    public void getBuySettingsFromServer() {
        buySettingsManager.getBuySettings()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onBuySettingsReceived, this::onError);
    }

    private void onBuySettingsReceived(BuySettings buySettings) {
        this.buySettings = buySettings;
        this.previousSettings = new BuySettings(buySettings.getId(), buySettings.isOn(), buySettings.isEemPrice(), buySettings.isEemPlusPrice(), buySettings.getEemPriceValue(), buySettings.getEemPlusPriceValue(), buySettings.isAllNeighboursSelected());
        getNeighboursFromServer();
        radioChanged.accept(new Event());
        notifyChange();
    }

    /**
     * SAVE
     * **/

    public void onSaveClick() {
        alertDialog.accept(new OpenDialogEvent());
    }

    public void save(){
        if(buySettingsChanged()) {
            buySettingsManager.updateBuySettings(buySettings)
                    .compose(schedulersTransformSingleIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onUpdateComplete, this::onError);
        }else if(neighboursToUpdate.size()>0){
            updateNeighbours();
        }

        if(timersToUpdate.size()>0){
            for (TimeInterval t: timersToUpdate.values()){
                buySettingsManager.updateTimeInterval(t)
                        .compose(schedulersTransformSingleIo())
                        .doOnSubscribe(this::addDisposable)
                        .subscribe(this::onTimeIntervalUpdate, this::onError);
            }
        }

    }

    private void updateNeighbours() {
        buySettingsManager.updateNeighboursBuy(new ArrayList<>(neighboursToUpdate.values()))
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onNeighboursUpdate, this::onError);
    }

    private void onTimeIntervalUpdate(TimeInterval timeInterval) {
        timersToUpdate.clear();
        notifyPropertyChanged(BR.saveVisible);
    }

    private void onNeighboursUpdate(String s) {
        neighboursToUpdate.clear();
        notifyPropertyChanged(BR.saveVisible);
    }

    private void onUpdateComplete(BuySettings buySettings) {

        getUiEvents().showToast(getResourceProvider().getString(R.string.msg_update_sucess));
        this.previousSettings = new BuySettings(buySettings.getId(), buySettings.isOn(), buySettings.isEemPrice(), buySettings.isEemPlusPrice(), buySettings.getEemPriceValue(), buySettings.getEemPlusPriceValue(), buySettings.isAllNeighboursSelected());
        this.buySettings = buySettings;
        radioChanged.accept(new Event());
        notifyPropertyChanged(BR.plusPriceValue);
        notifyPropertyChanged(BR.saveVisible);
        if (neighboursToUpdate.size()>0){
            updateNeighbours();
        }
    }

    Observable<NavigationEvent> observeOpenPriceInfo(){
        return openPriceInfoObservable;
    }

    Observable<NavigationEvent> observeOpenTimer(){
        return openTimerObservable;
    }

    Observable<Event> observeNeighbours(){
        return neighboursChanged;
    }

    Observable<Event> observeNeighboursState(){
        return neighboursStateChanged;
    }

    Observable<Event> observeTimeIntervalState(){
        return timeIntervalsStateChanged;
    }

    Observable<Event> observeRadio(){
        return radioChanged;
    }

    Observable<OpenDialogEvent> observeAlertDialog(){
        return alertDialog;
    }
}
