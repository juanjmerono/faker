package es.um.atica.faker.users.domain;

import java.util.UUID;

public class User {
    private UUID id;
    private UserName name;

    private User(UUID id, UserName name) {
        this.id = id; this.name = name;
    }

    public static User of (UUID id, UserName name) {
        return new User(id,name);
    }

    public UUID getId() { return id; }
    public UserName getName() { return name; }
}
