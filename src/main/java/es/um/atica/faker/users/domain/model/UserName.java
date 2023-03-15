package es.um.atica.faker.users.domain.model;

import java.util.Objects;

import org.springframework.util.StringUtils;

public class UserName {
    private String value;
    private UserName(String value) { this.value = value; }
    public static UserName of(String value) {
        Objects.requireNonNull(value, "User name could not be null!");
        if (!StringUtils.hasText(value)) throw new IllegalArgumentException("User name not valid!");
        return new UserName(value);
    }
    public String getValue() { return value; }
}
