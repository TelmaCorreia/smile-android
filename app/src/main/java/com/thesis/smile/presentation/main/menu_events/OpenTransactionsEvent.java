package com.thesis.smile.presentation.main.menu_events;

public class OpenTransactionsEvent extends OpenMenuEvent {

    public OpenTransactionsEvent() {
        this(true);
    }

    public OpenTransactionsEvent(boolean clearStack) {
        super(MenuType.TRANSACTIONS, clearStack);

    }

}
