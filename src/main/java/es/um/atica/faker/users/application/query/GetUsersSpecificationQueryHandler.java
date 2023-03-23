package es.um.atica.faker.users.application.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.service.UsersPaginatedReadService;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.cqrs.QueryHandler;

@Component
public class GetUsersSpecificationQueryHandler implements QueryHandler<Iterable<User>,GetUsersSpecificationQuery> {

    @Autowired
    private UsersPaginatedReadService usersPaginatedReadService;

    @Override
    public Iterable<User> handle(GetUsersSpecificationQuery query) throws Exception {
        return usersPaginatedReadService.findAllUsersSpecification(
            usersPaginatedReadService.specificationService()
                .buildSpecificationFromSearch(query.getSearchList()),
            query.getPage(),
            query.getPageSize()
        );
    }
}
