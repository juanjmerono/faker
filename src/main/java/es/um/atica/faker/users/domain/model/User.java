package es.um.atica.faker.users.domain.model;

import es.um.atica.faker.users.domain.event.UserCreated;
import es.um.atica.faker.users.domain.event.UserDeleted;
import es.um.atica.faker.users.domain.event.UserUpdated;
import es.um.atica.shared.domain.ddd.AggregateRoot;
import es.um.atica.shared.domain.events.Event;
import es.um.atica.shared.domain.events.EventCollection;

public class User implements AggregateRoot {

    private UserId id;
    private UserName name;
    private EventCollection events = new EventCollection();

    private User(UserId id, UserName name) {
        this.id = id; this.name = name;
    }

    public static User of (UserId id, UserName name) {
        return new User(id,name);
    }

    public UserId getId() { return id; }
    public UserName getName() { return name; }

    public static User createUser(UserId id, UserName name) {
        User usr = User.of(id,name);
        usr.addEvent(UserCreated.of(usr));
        return usr;
    }

    public void updateUser(UserName name) {
        this.name = name;
        this.addEvent(UserUpdated.of(this));
    }

    public void deleteUser() {
        this.addEvent(UserDeleted.of(this));
    }

    @Override
    public EventCollection getEvents() { return events; }

    @Override
    public void addEvent(Event event) { events.add(event); }

}