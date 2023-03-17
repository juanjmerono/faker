package es.um.atica.faker.users.adapters.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.um.atica.faker.users.application.ports.UsersReadRepository;
import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.faker.users.domain.model.User;

@Service
@org.springframework.context.annotation.Primary
public class DBUsersReadWriteRepository implements UsersReadRepository,UsersWriteRepository {

    @Autowired
    private JPAUsersReadRepository jpaUsersReadRepository;
    

    @Override
    public void saveUser(User user) {
        jpaUsersReadRepository.save(UserEntity.of(user));
    }

    @Override
    public void deleteUser(User user) {
        jpaUsersReadRepository.delete(UserEntity.of(user));
    }

    @Override
    public Iterable<User> findAllUsers(int page, int pageSize) {
        return jpaUsersReadRepository.findAll(PageRequest.of(page,pageSize)).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findUser(String userId) {
        return jpaUsersReadRepository.findById(userId).map(UserEntity::toModel);
    }
    
}
