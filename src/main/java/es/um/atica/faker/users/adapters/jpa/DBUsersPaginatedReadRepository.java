package es.um.atica.faker.users.adapters.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import es.um.atica.faker.users.application.service.UsersPaginatedReadService;
import es.um.atica.faker.users.application.service.UsersSearchSpecificationService;
import es.um.atica.faker.users.domain.model.User;

@Service
//@org.springframework.context.annotation.Primary
public class DBUsersPaginatedReadRepository implements UsersPaginatedReadService {

    @Autowired
    private JPAUsersReadRepository jpaUsersReadRepository;

    @Autowired
    private JPAUsersSpecification jpaUsersSpecification;
    
    @Override
    public Iterable<User> findAllUsers(int page, int pageSize) {
        return jpaUsersReadRepository.findAll(PageRequest.of(page,pageSize)).map(UserEntity::toModel);
    }

    @Override
    public Iterable<User> findAllUsersSpecification(Object specification, int page, int pageSize) {
        return jpaUsersReadRepository.findAll((Specification<UserEntity>)specification,PageRequest.of(page,pageSize)).map(UserEntity::toModel);
    }

    @Override
    public UsersSearchSpecificationService specificationService() {
        return jpaUsersSpecification;
    }

}
