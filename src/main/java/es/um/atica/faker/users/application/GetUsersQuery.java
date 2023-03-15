package es.um.atica.faker.users.application;

import es.um.atica.faker.users.domain.User;
import es.um.atica.shared.domain.cqrs.Query;

public class GetUsersQuery extends Query<Iterable<User>> {
    private GetUsersQuery() {}
    public static GetUsersQuery of() {
        return new GetUsersQuery();
    }
}
