package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.events.Event;

public class UserUpdated extends Event {

    private String userId;

    private UserUpdated(String id) {
        this.userId = id;
    }

    public static UserUpdated of (User user) {
        return new UserUpdated(user.getId().toString());
    }

    public String getUserId() { return userId; }

    @Override
    public String getAggregateId() {
        return this.getUserId();
    }
}
