package es.um.atica.faker.users.adapters.fake;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import es.um.atica.faker.users.application.ports.UsersReadRepository;
import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserName;

@Service
//@org.springframework.context.annotation.Primary
public class FakeUsersReadWriteRepository implements UsersReadRepository,UsersWriteRepository {

    private Map<String,User> users = new HashMap<>();

    public FakeUsersReadWriteRepository() {
        Faker faker = Faker.instance();
        for (int k=0; k<faker.random().nextInt(10, 20); k++) {
            User u = User.of(UUID.randomUUID(),UserName.of(faker.name().fullName()));
            users.put(u.getId().toString(),u);
        }
        users.put("30497182-c376-11ed-afa1-0242ac120002",User.of(UUID.fromString("30497182-c376-11ed-afa1-0242ac120002"),UserName.of(faker.name().fullName())));
        users.put("30497182-c376-11ed-afa1-0242ac220002",User.of(UUID.fromString("30497182-c376-11ed-afa1-0242ac220002"),UserName.of(faker.name().fullName())));
    }

    @Override
    public Iterable<User> findAllUsers(int page, int pageSize) {
        return new PageImpl<>(new ArrayList<User>(users.values()));
    }

    @Override
    public Optional<User> findUser(String userId) {
        return users.containsKey(userId)?Optional.of(users.get(userId)):Optional.empty();
    }

    @Override
    public void saveUser(User user) {
        users.put(user.getId().toString(),user);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getId().toString());
    }
    
}
