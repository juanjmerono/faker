package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserId;

public class UserUpdated extends UserEvent {

    private String changelog;

    public UserUpdated() { super(); }

    private UserUpdated(UserId id, String changelog) {
        super(id);
        this.changelog = changelog;
    }

    public static UserUpdated of (User user, String changelog) {
        return new UserUpdated(user.getId(), changelog);
    }

    public String getChangelog() { return changelog; }

    @Override
    public String toString() { 
        return super.toString() + String.format("[chlog:%s]",changelog);
    }

}
