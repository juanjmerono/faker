package es.um.atica.faker.users.domain.model;

public class UserAge {

    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 180;

    private int value;
    private UserAge(int value) { this.value = value; }
    public static UserAge of(int value) {
        if (value<MIN_AGE) throw new IllegalArgumentException(String.format("User age could not be below %d!",MIN_AGE));
        if (value>MAX_AGE) throw new IllegalArgumentException(String.format("User age could not be over %d!",MAX_AGE));
        return new UserAge(value);
    }
    public int getValue() { return value; }
}
