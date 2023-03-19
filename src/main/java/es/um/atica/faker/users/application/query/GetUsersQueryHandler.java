package es.um.atica.faker.users.application.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.service.UsersPaginatedReadService;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.cqrs.QueryHandler;

@Component
public class GetUsersQueryHandler implements QueryHandler<Iterable<User>,GetUsersQuery> {

    @Autowired
    private UsersPaginatedReadService usersService;

    @Override
    public Iterable<User> handle(GetUsersQuery query) throws Exception {
        return usersService.findAllUsers(query.getPage(),query.getPageSize());
    }
}
