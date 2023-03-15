package es.um.atica.faker.users.domain;

import java.util.Objects;

public class UserName {
    private String value;
    private UserName(String value) { this.value = value; }
    public static UserName of(String value) {
        Objects.requireNonNull(value, "User name could not be null!");
        if (!value.matches("[a-zA-Z0-9]{5,100}")) throw new IllegalArgumentException("User name not valid!");
        return new UserName(value);
    }
    public String getValue() { return value; }
}
