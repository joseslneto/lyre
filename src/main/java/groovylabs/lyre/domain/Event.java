package groovylabs.lyre.domain;

import groovylabs.lyre.domain.enums.EventAction;
import groovylabs.lyre.domain.enums.Queue;

public class Event {

    private Queue queue;

    private Enum<EventAction> action;

    public Event(Queue queue, EventAction action) {
        this.queue = queue;
        this.action = action;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Enum<EventAction> getAction() {
        return action;
    }

    public void setAction(Enum<EventAction> action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Event{" +
            "action='" + action +
            '}';
    }
}