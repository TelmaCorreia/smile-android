package com.thesis.smile.presentation.main.menu_events;

public class OpenHistoricalEvent extends OpenMenuEvent {

    public OpenHistoricalEvent() {
        this(true);
    }

    public OpenHistoricalEvent(boolean clearStack) {
        super(MenuType.HISTORICAL, clearStack);

    }

}
