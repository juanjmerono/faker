package es.um.atica.faker.users.domain.model;

import java.util.Collections;
import java.util.List;

import es.um.atica.faker.users.domain.event.UserCreated;
import es.um.atica.faker.users.domain.event.UserDeleted;
import es.um.atica.faker.users.domain.event.UserUpdated;
import es.um.atica.shared.domain.ddd.AggregateRoot;
import es.um.atica.shared.domain.events.Event;
import es.um.atica.shared.domain.events.EventCollection;

public class User implements AggregateRoot {

    private UserId id;
    private UserName name;
    private UserAge age;
    private UserOriginCountry country;
    private List<UserPreference> preferences;
    private EventCollection events = new EventCollection();

    private User(UserId id, UserName name, UserAge age, UserOriginCountry country, List<UserPreference> preferences) {
        this.id = id; this.name = name; this.age = age; this.country = country; this.preferences = preferences;
    }

    public static User of (UserId id, UserName name, UserAge age, UserOriginCountry country) {
        return new User(id,name,age,country,Collections.emptyList());
    }

    public static User of (UserId id, UserName name, UserAge age, UserOriginCountry country, List<UserPreference> preferences) {
        return new User(id,name,age,country,preferences);
    }

    public UserId getId() { return id; }
    public UserName getName() { return name; }
    public UserAge getAge() { return age; }
    public UserOriginCountry getCountry() { return country; }
    public List<UserPreference> getPreferences() { return Collections.unmodifiableList(preferences); }

    public void createUser() {
        this.addEvent(UserCreated.of(this));
    }

    public void updateUserName(UserName name) {
        this.name = name;
        this.addEvent(UserUpdated.of(this,"name changed"));
    }

    public void updateUserAge(UserAge age) {
        this.age = age;
        this.addEvent(UserUpdated.of(this,"age changed"));
    }

    public void deleteUser() {
        this.addEvent(UserDeleted.of(this));
    }

    @Override
    public EventCollection getEvents() { return events; }

    @Override
    public void addEvent(Event event) { events.add(event); }

}