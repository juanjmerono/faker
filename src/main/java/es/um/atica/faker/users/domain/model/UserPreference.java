package es.um.atica.faker.users.domain.model;

import java.util.Objects;

public class UserPreference {

    public static final int MIN_RANGE = 0;
    public static final int MAX_RANGE = 10;

    private String name;
    private int quantity;
    private UserPreference(String name, int quantity) { this.name = name; this.quantity = quantity; }
    public static UserPreference of(String name, int quantity) {
        Objects.requireNonNull(name, "Preference name could not be null!");
        if (name.trim().isEmpty()) throw new IllegalArgumentException("Preference name could not be empty!");
        if (quantity<MIN_RANGE || quantity>MAX_RANGE) 
            throw new IllegalArgumentException(String
                .format("Preference quantity %d should be between %d and %d!",
                                quantity,MIN_RANGE,MAX_RANGE));
        return new UserPreference(name,quantity);
    }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
}
