package es.um.atica.faker.users.application.query;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.repository.UsersReadRepository;
import es.um.atica.shared.domain.cqrs.QueryHandler;

@Component
public class GetUserQueryHandler implements QueryHandler<Optional<User>,GetUserQuery> {

    @Autowired
    private UsersReadRepository usersRepository;

    @Override
    public Optional<User> handle(GetUserQuery query) throws Exception {
        return usersRepository.findUser(query.getUserId());
    }
}
