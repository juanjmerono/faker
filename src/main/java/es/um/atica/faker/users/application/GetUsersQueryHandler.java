package es.um.atica.faker.users.application;

import java.util.Collections;

import org.springframework.stereotype.Component;

import es.um.atica.faker.users.domain.User;
import es.um.atica.shared.domain.cqrs.QueryHandler;

@Component
public class GetUsersQueryHandler implements QueryHandler<Iterable<User>,GetUsersQuery> {

    @Override
    public Iterable<User> handle(GetUsersQuery query) throws Exception {
        return Collections.emptyList();
    }
}
