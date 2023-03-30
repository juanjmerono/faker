package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserId;

public class UserCreated extends UserEvent {

    public UserCreated() { super(); }

    private UserCreated(UserId id) { super(id); }

    public static UserCreated of (User user) {
        return new UserCreated(user.getId());
    }

}
