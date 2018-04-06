package com.thesis.smile.presentation.main.menu_events;

import com.thesis.smile.presentation.utils.actions.events.Event;

public class OpenMenuEvent extends Event {

    private MenuType menuType;
    private boolean clearStack;

    OpenMenuEvent(MenuType menuType) {
        this(menuType, true);
    }

    OpenMenuEvent(MenuType menuType, boolean clearStack) {
        this.menuType = menuType;
        this.clearStack = clearStack;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public boolean mustClearStack() {
        return clearStack;
    }
}