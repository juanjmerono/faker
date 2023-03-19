package es.um.atica.faker.users.domain.model;

import java.util.Objects;

public class UserName {
    private String value;
    private UserName(String value) { this.value = value; }
    public static UserName of(String value) {
        Objects.requireNonNull(value, "User name could not be null!");
        if (value.trim().isEmpty()) throw new IllegalArgumentException("User name could not be empty!");
        return new UserName(value);
    }
    public String getValue() { return value; }
}
