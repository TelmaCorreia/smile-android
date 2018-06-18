package com.thesis.smile.presentation.main.ranking;

import com.jakewharton.rxrelay2.PublishRelay;
import com.thesis.smile.domain.managers.RankingsManager;
import com.thesis.smile.domain.models.Ranking;
import com.thesis.smile.domain.models.RankingHeader;
import com.thesis.smile.domain.models.RankingModel;
import com.thesis.smile.domain.models.RankingModelList;
import com.thesis.smile.presentation.base.BaseViewModel;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import org.threeten.bp.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RankingViewModel extends BaseViewModel {

    private final List<RankingHeader> rankingHeaders;

    private RankingsManager rankingsManager;

    private PublishRelay<Event> rankingsChanged = PublishRelay.create();

    @Inject
    public RankingViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, RankingsManager rankingsManager) {
        super(resourceProvider, schedulerProvider, uiEvents);
        this.rankingsManager = rankingsManager;
        this.rankingHeaders = new ArrayList<>();
        getRankingFromServer();

    }

    public List<RankingHeader> getRanking() {
        return rankingHeaders;
    }

    public void getRankingFromServer() {
        rankingsManager.getRankingRenewablesUsage()
            .doOnSubscribe(this::addDisposable)
            .compose(schedulersTransformSingleIo())
            .subscribe(this::onRankingReceived, this::onError);
    }

    private void onRankingReceived(List<RankingModelList> rankingModelLists) {
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



    Observable<Event> observeRankings(){
        return rankingsChanged;
    }

}
