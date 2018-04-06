package com.thesis.smile.presentation.main.menu_events;

public class OpenRankingEvent extends OpenMenuEvent {

    public OpenRankingEvent() {
        this(true);
    }

    public OpenRankingEvent(boolean clearStack) {
        super(MenuType.RANKING, clearStack);

    }

}
