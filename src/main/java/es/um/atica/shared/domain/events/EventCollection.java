package es.um.atica.shared.domain.events;

import java.util.ArrayList;
import java.util.List;

public class EventCollection {

    private List<Event> events;

    public EventCollection() {
        this.events = new ArrayList<>();
    }

    public List<Event> getAll() {
        return events;
    }

    public void add(Event event) {
        events.add(event);
    }

    public void clear() {
        events.clear();
    }

    public void publish(EventBus eventBus) {
        eventBus.publish(events);
        clear();
    }
}