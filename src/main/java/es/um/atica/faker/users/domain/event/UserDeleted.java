package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.events.Event;

public class UserDeleted extends Event {

    private String userId;

    private UserDeleted(String id) {
        this.userId = id;
    }

    public static UserDeleted of (User user) {
        return new UserDeleted(user.getId().getValue());
    }

    public String getUserId() { return userId; }

    @Override
    public String getAggregateId() {
        return this.getUserId();
    }
}
