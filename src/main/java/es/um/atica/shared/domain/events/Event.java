package es.um.atica.shared.domain.events;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public abstract class Event {

    private EventId id;
    private LocalDateTime date;

    public Event() {
        this(Collections.emptyMap());
    }

    public Event(Map<String, Object> data) {
        this.id = new EventId(UUID.randomUUID());
        this.date = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public EventId getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public abstract String getAggregateId();

}