package es.um.atica.shared.domain.ddd;

import es.um.atica.shared.domain.events.Event;
import es.um.atica.shared.domain.events.EventCollection;

public interface AggregateRoot {
    EventCollection getEvents();
    void addEvent(Event event);
}