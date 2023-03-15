package es.um.atica.faker.users.application.services.query;

import java.util.Optional;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.cqrs.Query;

public class GetUserQuery extends Query<Optional<User>> {
    private String userId;
    private GetUserQuery(String userId) {
        this.userId = userId;
    }
    public static GetUserQuery of(String userId) {
        return new GetUserQuery(userId);
    }

    public String getUserId() { return userId; }
}
