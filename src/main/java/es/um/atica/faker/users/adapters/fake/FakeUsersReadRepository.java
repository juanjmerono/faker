package es.um.atica.faker.users.adapters.fake;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import es.um.atica.faker.users.application.ports.UsersReadRepository;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserName;

@Service
public class FakeUsersReadRepository implements UsersReadRepository {

    @Override
    public Iterable<User> findAllUsers(int page, int pageSize) {
        Faker faker = Faker.instance();
        return new PageImpl<>(IntStream.rangeClosed(1, faker.random().nextInt(10, 20)).boxed()
            .map(i->User.of(UUID.randomUUID(),UserName.of(faker.name().fullName())))
            .collect(Collectors.toList()));
    }

    @Override
    public Optional<User> findUser(String userId) {
        Faker faker = Faker.instance();
        return Optional.of(User.of(UUID.fromString(userId),UserName.of(faker.name().fullName())));
    }
    
}
