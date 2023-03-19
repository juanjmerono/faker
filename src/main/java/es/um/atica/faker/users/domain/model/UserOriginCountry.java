package es.um.atica.faker.users.domain.model;

import java.util.Objects;

public class UserOriginCountry {
    private String value;
    private UserOriginCountry(String value) { this.value = value; }
    public static UserOriginCountry of(String value) {
        Objects.requireNonNull(value, "User origin country could not be null!");
        if (value.trim().isEmpty()) throw new IllegalArgumentException("User origin country could not be empty!");
        return new UserOriginCountry(value);
    }
    public String getValue() { return value; }
}
