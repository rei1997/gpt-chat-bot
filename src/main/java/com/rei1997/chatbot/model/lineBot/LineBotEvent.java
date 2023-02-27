package com.rei1997.chatbot.model.lineBot;

import java.util.List;

public class LineBotEvent {
    private String destination;
    private List<Event> events;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}