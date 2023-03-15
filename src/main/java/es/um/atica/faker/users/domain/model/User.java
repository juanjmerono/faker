package es.um.atica.faker.users.domain.model;

import java.util.UUID;

import es.um.atica.faker.users.domain.event.UserCreated;
import es.um.atica.shared.domain.ddd.AggregateRoot;
import es.um.atica.shared.domain.events.Event;
import es.um.atica.shared.domain.events.EventCollection;

public class User implements AggregateRoot {

    private UUID id;
    private UserName name;
    private EventCollection events = new EventCollection();

    private User(UUID id, UserName name) {
        this.id = id; this.name = name;
    }

    public static User of (UUID id, UserName name) {
        return new User(id,name);
    }

    public UUID getId() { return id; }
    public UserName getName() { return name; }

    public static User createUser(UUID id, UserName name) {
        User usr = User.of(id,name);
        usr.addEvent(UserCreated.of(usr));
        return usr;
    }

    @Override
    public EventCollection getEvents() { return events; }

    @Override
    public void addEvent(Event event) { events.add(event); }

}