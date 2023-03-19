package es.um.atica.faker.users.domain.repository;

import java.util.Optional;

import es.um.atica.faker.users.domain.model.User;

public interface UsersReadRepository {
    
    public Iterable<User> findAllUsers();
    public Optional<User> findUser(String userId);

}
