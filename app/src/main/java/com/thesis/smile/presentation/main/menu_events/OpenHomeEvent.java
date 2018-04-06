package com.thesis.smile.presentation.main.menu_events;

public class OpenHomeEvent extends OpenMenuEvent {

    public OpenHomeEvent() {
        this(true);
    }

    public OpenHomeEvent(boolean clearStack) {
        super(MenuType.HOME, clearStack);

    }

}
