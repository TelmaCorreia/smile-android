package com.thesis.smile.presentation.info_price;

import android.databinding.Bindable;
import android.view.View;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.R;
import com.thesis.smile.domain.managers.TransactionsSettingsManager;
import com.thesis.smile.domain.models.InfoPrice;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.NavigationEvent;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class InfoPriceViewModel extends BaseViewModel {

    private List<Integer> selectedDays = new ArrayList<>();
    private PublishRelay<NavigationEvent> closeObservable = PublishRelay.create();
    private InfoPrice infoPrice;
    
    private TransactionsSettingsManager transactionsSettingsManager;

    @Inject
    public InfoPriceViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, TransactionsSettingsManager transactionsSettingsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.transactionsSettingsManager=transactionsSettingsManager;
        getInfoPriceFromServer();
    }

    @Bindable
    public String getPriceOne() {
        if (infoPrice!=null){
            return infoPrice.getPriceVazio();
        }
        return null;
    }
    
    @Bindable
    public String getTextOne() {
        return getResourceProvider().getString(R.string.price_info_subtitle_vazio);
    }

    @Bindable
    public String getPriceTwo() {
        if (infoPrice!=null){
            if (!infoPrice.getPriceForaVazio().equals("0.0")) {
                return infoPrice.getPriceForaVazio();
            }
            else if(!infoPrice.getPricePonta().equals("0.0")){
                return infoPrice.getPricePonta();
            }

        }
        return null;
    }

    @Bindable
    public String getTextTwo() {
        if (infoPrice!=null){
            if (!infoPrice.getPriceForaVazio().equals("0.0")) {
                return getResourceProvider().getString(R.string.price_info_subtitle_fora_vazio);
            }
            else if(!infoPrice.getPricePonta().equals("0.0")){
                return getResourceProvider().getString(R.string.price_info_subtitle_ponta);
            }

        }
        return null;
    }

    @Bindable
    public boolean isTwoVisible(){
        if(infoPrice!=null){
            return !infoPrice.getPriceForaVazio().equals("0.0") ||!infoPrice.getPricePonta().equals("0.0");
        }
        return false;
    }

    @Bindable
    public String getPriceThree() {
        if (infoPrice!=null){
            if (!infoPrice.getPriceCheia().equals("0.0")) {
                return infoPrice.getPriceCheia();
            }

        }
        return null;
    }

    @Bindable
    public String getTextThree() {
        if (infoPrice!=null){
            if (!infoPrice.getPriceCheia().equals("0.0")) {
                return getResourceProvider().getString(R.string.price_info_subtitle_cheia);
            }
        }
        return null;
    }

    @Bindable
    public boolean isThreeVisible(){
        if(infoPrice!=null){
            return !infoPrice.getPriceCheia().equals("0.0");
        }
        return false;
    }

    @Bindable
    public String getLink() {
        return "https://www.eem.pt/media/324637/monofolha_tarif_btn_ts_2018.pdf"; 
    }

    public void onCloseClick(){
        closeObservable.accept(new NavigationEvent());
    }

    Observable<NavigationEvent> observeClose(){
        return closeObservable;
    }

    public void getInfoPriceFromServer() {
        transactionsSettingsManager.getInfoPrice()
                .doOnSubscribe(this::addDisposable)
                .compose(schedulersTransformSingleIo())
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::onInfoPriceReceived, this::onError);
    }

    public void onInfoPriceReceived(InfoPrice infoPrice) {
        this.infoPrice=infoPrice;
        notifyChange();
    }


}
