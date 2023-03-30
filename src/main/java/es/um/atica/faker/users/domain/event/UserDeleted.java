package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserId;

public class UserDeleted extends UserEvent {

    public UserDeleted() { super(); }

    private UserDeleted(UserId id) { super(id); }

    public static UserDeleted of (User user) {
        return new UserDeleted(user.getId());
    }

}
