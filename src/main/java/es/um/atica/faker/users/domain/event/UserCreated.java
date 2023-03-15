package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.events.Event;

public class UserCreated extends Event {

    private String userId;

    private UserCreated(String id) {
        this.userId = id;
    }

    public static UserCreated of (User user) {
        return new UserCreated(user.getId().toString());
    }

    public String getUserId() { return userId; }
}
