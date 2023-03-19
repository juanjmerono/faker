package es.um.atica.faker.users.domain.model;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private UUID value;
    private UserId(UUID value) { this.value = value; }
    public static UserId of(String value) {
        Objects.requireNonNull(value, "User Id could not be null!");
        return new UserId(UUID.fromString(value));
    }
    public static UserId randomId() {
        return UserId.of(UUID.randomUUID().toString());
    }
    public String getValue() { return value.toString(); }    
}
