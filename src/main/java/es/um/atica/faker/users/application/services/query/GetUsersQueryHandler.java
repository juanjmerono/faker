package es.um.atica.faker.users.application.services.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.ports.UsersReadRepository;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.cqrs.QueryHandler;

@Component
public class GetUsersQueryHandler implements QueryHandler<Iterable<User>,GetUsersQuery> {

    @Autowired
    private UsersReadRepository usersRepository;

    @Override
    public Iterable<User> handle(GetUsersQuery query) throws Exception {
        return usersRepository.findAllUsers(query.getPage(),query.getPageSize());
    }
}
