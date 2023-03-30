package es.um.atica.shared.domain.events;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public abstract class Event {

    private UUID id;
    private LocalDateTime date;
    private String type;

    public Event() {
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now(ZoneId.of("UTC"));
        this.type = String.format(this.getTypeFormat(),this.getClass().getName());
    }

    public String getId() { return this.id.toString(); }

    public String getDate() { return this.date.toString(); }

    public String getType() { return this.type; }

    public String toString() {
        return String.format("[id:%s][date:%s][type:%s]",id,date,type);
    }

    public abstract String getTypeFormat();

    public abstract String getAggregateId();

}