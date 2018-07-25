package com.thesis.smile.presentation.main.ranking;

import android.databinding.Bindable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.BR;
import com.thesis.smile.domain.managers.RankingsManager;
import com.thesis.smile.domain.managers.UserManager;
import com.thesis.smile.domain.models.RankingHeader;
import com.thesis.smile.domain.models.RankingModelList;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RankingViewModel extends BaseViewModel {

    private final List<RankingHeader> rankingHeaders;
    private RankingsManager rankingsManager;
    private boolean loading = false;
    private PublishRelay<Event> rankingsChanged = PublishRelay.create();
    private UserManager userManager;

    @Inject
    public RankingViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, RankingsManager rankingsManager, UserManager userManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.rankingsManager = rankingsManager;
        this.rankingHeaders = new ArrayList<>();
        this.userManager = userManager;
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Ranking")
                .putContentType("Section Ranking")
                .putContentId("ranking")
                .putCustomAttribute("email", userManager.getCurrentUser().getEmail()));
        getRankingFromServer();

    }

    public List<RankingHeader> getRanking() {
        return rankingHeaders;
    }

    @Bindable
    public boolean isProgress(){
        return loading;
    }

    public void setProgress(boolean loading){
        this.loading = loading;
        notifyPropertyChanged(BR.progress);
    }

    public void getRankingFromServer() {
        setProgress(true);
        rankingsManager.getRankingRenewablesUsage()
            .doOnSubscribe(this::addDisposable)
            .compose(schedulersTransformSingleIo())
            .subscribe(this::onRankingReceived, this::onError);
    }

    private void onRankingReceived(List<RankingModelList> rankingModelLists) {
        setProgress(false);
        int pos = 0;
        for (RankingModelList list : rankingModelLists){
            String title = (list.getRankings().size()>0)?list.getRankings().get(0).getDate().toString():"empty";
            if (pos==0){
                title = "Esta semana";
                pos++;
            }else if (pos==1){
                title = "Semana passada";
                pos++;
            }
            rankingHeaders.add(new RankingHeader(title, list.getRankings()));
        }
        rankingsChanged.accept(new Event());
    }

    @Override
    public void onError(Throwable e){
        setProgress(false);
        super.onError(e);
    }

    Observable<Event> observeRankings(){
        return rankingsChanged;
    }

}
