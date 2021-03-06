package com.thesis.smile.presentation.main.transactions.sell;

import android.databinding.Bindable;
import android.databinding.ObservableList;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsSettingsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.SellSettings;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.presentation.utils.actions.events.OpenDialogEvent;
import com.thesis.smile.presentation.utils.databinding.ExclusiveObservableList;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;
import com.thesis.smile.BR;


import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SellViewModel extends BaseViewModel {

    private final ExclusiveObservableList<TimeInterval> timeIntervals;
    private final List<Neighbour> neighbours;
    private Map<String, Neighbour> neighboursToUpdate;
    private SellSettings sellSettings;
    private SellSettings previousSettings;
    private TransactionsSettingsManager sellSettingsManager;
    private UserManager userManager;
    private Map<String, TimeInterval> timersToUpdate;
    private PublishRelay<OpenDialogEvent> alertDialog = PublishRelay.create();
    private PublishRelay<NavigationEvent> openPriceInfoObservable = PublishRelay.create();
    private PublishRelay<NavigationEvent> openTimerObservable = PublishRelay.create();
    private PublishRelay<Event> neighboursChanged = PublishRelay.create();
    private PublishRelay<Event> neighboursStateChanged = PublishRelay.create();
    private PublishRelay<Event> timeIntervalsStateChanged = PublishRelay.create();
    private PublishRelay<Event> radioChanged = PublishRelay.create();
    private PublishRelay<Event> sliderChanged = PublishRelay.create();
    private PublishRelay<Event> switchChanged = PublishRelay.create();

    @Inject
    public SellViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents,  TransactionsSettingsManager sellSettingsManager, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.sellSettingsManager = sellSettingsManager;
        timeIntervals = new ExclusiveObservableList<>();
        neighbours = new ArrayList<>();
        neighboursToUpdate = new HashMap<>();
        timersToUpdate = new HashMap<>();
        this.userManager = userManager;
        getTimeIntervalsFromServer();
        getSellSettingsFromServer();
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Transactions:sell settings")
                .putContentType("Section Transations")
                .putContentId("transactions_sell_settings")
                .putCustomAttribute("smid", userManager.getCurrentUser().getCons_smart_meter_id())
                .putCustomAttribute("hour", LocalTime.now().getHour()));
    }

    @Bindable
    public boolean isSell() {
        if (sellSettings!=null){
            return sellSettings.isOn();
        }
        return false;
    }

    public void setSell(boolean sell) {
        if(sellSettings!=null) {
            if (sellSettings.isSpecificPrice() && sellSettings.getSpecificPriceValue() == 0) {
                getUiEvents().showToast(getResourceProvider().getString(R.string.alert_price));
                switchChanged.accept(new Event());
            } else {
                this.sellSettings.setOn(sell);
                if (sell && sellSettings.isOn() && !previousSettings.isOn()) {
                    alertDialog.accept(new OpenDialogEvent());
                }

                notifyPropertyChanged(BR.sell);
                if (!sell) {
                    notifyPropertyChanged(BR.saveVisible);
                }
            }
        }
    }

    @Bindable
    public boolean isPlusPriceEditable() {
        if(sellSettings!=null){
            return sellSettings.isPlusPrice();
        }
        return false;
    }

    @Bindable
    public String getPlusPriceValue() {
        if(sellSettings!=null){
            return String.format("%.2f", sellSettings.getPlusPriceValue());
        }
        return null;
    }

    public void setPlusPriceValue(String plusPriceValue) {
        if(sellSettings!=null && sellSettings.isPlusPrice() && !plusPriceValue.isEmpty()){
            try{
                double value = Double.parseDouble(plusPriceValue.replace(',', '.'));
                sellSettings.setPlusPriceValue(value);
                notifyPropertyChanged(BR.saveVisible);
            }catch (NumberFormatException e){
                getUiEvents().showToast(getResourceProvider().getString(R.string.alert_only_numbers));
            }
        }
    }

    @Bindable
    public boolean isConcretePriceEditable() {
        if(sellSettings!=null){
            return sellSettings.isSpecificPrice();
        }
        return false;
    }

    @Bindable
    public String getSpecificPriceValue() {
        if(sellSettings!=null && sellSettings.isSpecificPrice()){
            return String.format("%.2f", sellSettings.getSpecificPriceValue());
        }
        return null;
    }

    public void setSpecificPriceValue(String specificPriceValue) {
        if(sellSettings!=null && !specificPriceValue.isEmpty()){
            try{
                double value = Double.parseDouble(specificPriceValue.replace(',', '.'));
                sellSettings.setSpecificPriceValue(value);
                notifyPropertyChanged(BR.saveVisible);
            }catch (NumberFormatException e){
                getUiEvents().showToast(getResourceProvider().getString(R.string.alert_only_numbers));
            }
        }
    }

    @Bindable
    public String getBatteryLevel() {
        if(sellSettings!=null){
            return String.format("%.2f", sellSettings.getBatteryLevel());
        }
        return null;
    }

    public void setBatteryLevel(String batteryLevel) {
        if(sellSettings!=null && batteryLevel.length()==4){
            try{
                double value = Double.parseDouble(batteryLevel.replace(',', '.'));
                sellSettings.setBatteryLevel(value);
                notifyPropertyChanged(BR.saveVisible);
                sliderChanged.accept(new Event());
            }catch (NumberFormatException e){
                getUiEvents().showToast(getResourceProvider().getString(R.string.alert_only_numbers));
            }
        }
    }

    public boolean isAllNeighboursSelected() {
        if(sellSettings!=null){
            return sellSettings.isAllNeighboursSelected();
        }
        return false;
    }

    public void setAllNeighboursSelected(boolean value) {
        if(sellSettings!=null){
            sellSettings.setAllNeighboursSelected(value);
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
        if(sellSettings!=null){
            sellSettings.setAllNeighboursSelected(false);
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
        if(sellSettings!=null) {
            return sellSettingsChanged() || neighboursToUpdate.size()>0 || timersToUpdate.size()>0;
        }
        return false;

    }

    private boolean sellSettingsChanged(){
        return !(sellSettings.isOn()== previousSettings.isOn()
                && sellSettings.isAllNeighboursSelected()==previousSettings.isAllNeighboursSelected()
                && sellSettings.isSpecificPrice()==previousSettings.isSpecificPrice()
                && sellSettings.isPlusPrice()==previousSettings.isPlusPrice()
                && sellSettings.getSpecificPriceValue()==previousSettings.getSpecificPriceValue()
                && sellSettings.getPlusPriceValue()==previousSettings.getPlusPriceValue()
                && sellSettings.getBatteryLevel()==previousSettings.getBatteryLevel());
    }

    public void onSpecificPriceClick(){
        if(sellSettings!=null){
            sellSettings.setSpecificPrice(!sellSettings.isSpecificPrice());
            sellSettings.setPlusPrice(!sellSettings.isPlusPrice());
            radioChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
            notifyPropertyChanged(BR.plusPriceEditable);
            notifyPropertyChanged(BR.concretePriceEditable);
        }
    }

    public void onPlusPriceClick(){
        if(sellSettings!=null){
            sellSettings.setSpecificPrice(!sellSettings.isSpecificPrice());
            sellSettings.setPlusPrice(!sellSettings.isPlusPrice());
            radioChanged.accept(new Event());
            notifyPropertyChanged(BR.saveVisible);
            notifyPropertyChanged(BR.plusPriceEditable);
            notifyPropertyChanged(BR.concretePriceEditable);
        }
    }

    public void onPriceInfoClick(){
        openPriceInfoObservable.accept(new NavigationEvent());
    }

    public void onAddTimerClick(){
        openTimerObservable.accept(new NavigationEvent());
    }

    public ObservableList<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public SellSettings getSellSettings() {
        return sellSettings;
    }

    /**
     * TIME INTERVALS
     * */

    public void getTimeIntervalsFromServer(){
        Disposable disposableTimeIntervals = sellSettingsManager.getTimeIntervalsSell()
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
            disposable = sellSettingsManager.postTimeIntervalSell(timeInterval)
                    .compose(schedulersTransformSingleIo())
                    .subscribe(this::onTimeIntervalReceived, this::onError);
        }else{
            disposable = sellSettingsManager.updateTimeInterval(timeInterval)
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

    public void removeTimerInterval(TimeInterval timeInterval){
        Disposable disposable = sellSettingsManager.deleteTimeInterval(timeInterval)
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
        Disposable disposableNeighbours = sellSettingsManager.getNeighboursSell(0, 20)
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

    public void getSellSettingsFromServer() {
        sellSettingsManager.getSellSettings()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onSellSettingsReceived, this::onError);
    }

    private void onSellSettingsReceived(SellSettings sellSettings) {
        this.sellSettings = sellSettings;
        this.previousSettings = new SellSettings(sellSettings.getId(), sellSettings.isOn(), sellSettings.isSpecificPrice(), sellSettings.isPlusPrice(), sellSettings.getSpecificPriceValue(), sellSettings.getPlusPriceValue(), sellSettings.getBatteryLevel(), sellSettings.isAllNeighboursSelected());
        getNeighboursFromServer();
        radioChanged.accept(new Event());
        sliderChanged.accept(new Event());
        notifyChange();
    }

    /**
     * SAVE
     * **/

    public void onSaveClick() {
        if(sellSettings.isSpecificPrice() && sellSettings.getSpecificPriceValue()==0){
            getUiEvents().showToast(getResourceProvider().getString(R.string.alert_price));
        }else{
            Answers.getInstance().logContentView(new ContentViewEvent()
                    .putContentName("Transactions:sell settings save")
                    .putContentType("Section Transactions")
                    .putContentId("transactions_sell_settings_save")
                    .putCustomAttribute("smid", userManager.getCurrentUser().getCons_smart_meter_id())
                    .putCustomAttribute("hour", LocalTime.now().getHour()));

            alertDialog.accept(new OpenDialogEvent());
        }
    }
    public void save(){
        if(sellSettingsChanged()) {
            sellSettingsManager.updateSellSettings(sellSettings)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onUpdateComplete, this::onError);
        }else if(neighboursToUpdate.size()>0){
           updateNeighbours();
        }

        if(timersToUpdate.size()>0){
            for (TimeInterval t: timersToUpdate.values()){
                sellSettingsManager.updateTimeInterval(t)
                    .compose(schedulersTransformSingleIo())
                    .doOnSubscribe(this::addDisposable)
                    .subscribe(this::onTimeIntervalUpdate, this::onError);
            }
        }

    }

    private void updateNeighbours() {
        sellSettingsManager.updateNeighboursSell(new ArrayList<>(neighboursToUpdate.values()))
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

    private void onUpdateComplete(SellSettings sellSettings) {

        getUiEvents().showToast(getResourceProvider().getString(R.string.msg_update_sucess));
        this.previousSettings = new SellSettings(sellSettings.getId(), sellSettings.isOn(), sellSettings.isSpecificPrice(), sellSettings.isPlusPrice(), sellSettings.getSpecificPriceValue(), sellSettings.getPlusPriceValue(), sellSettings.getBatteryLevel(), sellSettings.isAllNeighboursSelected());
        this.sellSettings = sellSettings;
        notifyPropertyChanged(BR.plusPriceValue);
        notifyPropertyChanged(BR.specificPriceValue);
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

    Observable<Event> observeSlider(){
        return sliderChanged;
    }
    Observable<Event> observeSwitch(){
        return switchChanged;
    }

    Observable<OpenDialogEvent> observeAlertDialog(){
        return alertDialog;
    }

}
