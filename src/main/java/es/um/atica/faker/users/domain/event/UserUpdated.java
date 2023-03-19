package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.events.Event;

public class UserUpdated extends Event {

    private String userId;
    private String changelog;

    private UserUpdated(String id, String changelog) {
        this.userId = id; this.changelog = changelog;
    }

    public static UserUpdated of (User user, String changelog) {
        return new UserUpdated(user.getId().getValue(), changelog);
    }

    public String getUserId() { return userId; }

    public String getChangelog() { return changelog; }

    @Override
    public String getAggregateId() {
        return this.getUserId();
    }
}
