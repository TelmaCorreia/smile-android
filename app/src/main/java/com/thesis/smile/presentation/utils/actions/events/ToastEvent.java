package com.thesis.smile.presentation.utils.actions.events;

public class ToastEvent extends Event {

    private String message;

    public ToastEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
