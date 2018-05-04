package com.thesis.smile.presentation.main.ranking;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.managers.RankingsManager;
import com.thesis.smile.domain.models.Neighbour;
import com.thesis.smile.domain.models.Ranking;
import com.thesis.smile.domain.models.RankingHeader;
import com.thesis.smile.domain.models.RankingHeaderTest;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RankingViewModel extends BaseViewModel {

    private final List<RankingHeaderTest> rankings;
    private RankingsManager rankingsManager;

    private PublishRelay<Event> rankingsChanged = PublishRelay.create();

    @Inject
    public RankingViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, RankingsManager rankingsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.rankingsManager = rankingsManager;
        this.rankings = new ArrayList<>();
        //TODO: ranking from server
      //  getRankingFromServer();

    }

    public List<RankingHeaderTest> getRanking() {
        return rankings;
    }

    public void getRankingFromServer() {

        rankingsManager.getRankingRenewablesUsage()
            .doOnSubscribe(this::addDisposable)
            .compose(schedulersTransformSingleIo())
            .subscribe(this::onRankingReceived, this::onError);
    }

    private void onRankingReceived(List<RankingHeaderTest> rankingHeaders) {
        this.rankings.addAll(rankingHeaders);
        rankingsChanged.accept(new Event());
    }

    Observable<Event> observeRankings(){
        return rankingsChanged;
    }

}
