package es.um.atica.faker.users.adapters.fake;

import org.springframework.stereotype.Service;

import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.faker.users.domain.model.User;

@Service
public class FakeUsersWriteRepository implements UsersWriteRepository {

    @Override
    public void saveUser(User user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteUser(User user) {
        // TODO Auto-generated method stub
    }
    
}
