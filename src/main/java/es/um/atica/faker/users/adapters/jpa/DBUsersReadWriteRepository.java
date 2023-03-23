package es.um.atica.faker.users.adapters.jpa;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.repository.UsersReadRepository;
import es.um.atica.faker.users.domain.repository.UsersWriteRepository;

@Service
//@org.springframework.context.annotation.Primary
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
    public Optional<User> findUser(String userId) {
        return jpaUsersReadRepository.findById(userId).map(UserEntity::toModel);
    }

    @Override
    public Iterable<User> findAllUsers() {
        return StreamSupport.stream(jpaUsersReadRepository.findAll().spliterator(), false)
            .map(UserEntity::toModel).collect(Collectors.toList());
    }
    
}
