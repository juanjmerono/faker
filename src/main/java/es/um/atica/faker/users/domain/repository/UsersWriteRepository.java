package es.um.atica.faker.users.domain.repository;

import es.um.atica.faker.users.domain.model.User;

public interface UsersWriteRepository {
    
    public void saveUser(User user);

    public void deleteUser(User user);

}
