package es.um.atica.faker.users.application.ports;

import java.util.Optional;

import es.um.atica.faker.users.domain.model.User;

public interface UsersReadRepository {
    
    public Iterable<User> findAllUsers(int page, int pageSize);

    public Optional<User> findUser(String userId);

}
