package es.um.atica.faker.users.adapters.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import es.um.atica.faker.users.application.service.UsersPaginatedReadService;
import es.um.atica.faker.users.application.service.UsersSearchSpecificationService;
import es.um.atica.faker.users.domain.factory.UsersFactory;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.faker.users.domain.model.UserOriginCountry;
import es.um.atica.faker.users.domain.repository.UsersReadRepository;
import es.um.atica.faker.users.domain.repository.UsersWriteRepository;
import es.um.atica.shared.domain.specification.Specification;

@Service
//@org.springframework.context.annotation.Primary
public class FakeUsersReadWriteRepository implements UsersReadRepository,UsersWriteRepository,UsersPaginatedReadService {

    private UsersSearchSpecificationService usersSearchSpecificationService = new UsersSearchSpecificationService() {
        @Override
        public Object defaultSpec() {
            return new Specification<User>(){
                @Override
                public boolean isSatisfied(User user) {
                    return user.isOverAge(18);
                }};
        }
        @Override
        public Object buildAndSpec(Object element1, Object element2) {
            return ((Specification<User>)element1).and((Specification<User>)element2);
        }
        @Override
        public Object buildOrSpec(Object element1, Object element2) {
            return ((Specification<User>)element1).or((Specification<User>)element2);
        }
        @Override
        public Object buildSpecFor(String el1, String op, String el2) {
            return new Specification<User>(){
                @Override
                public boolean isSatisfied(User user) {
                    switch(op) {
                        case ":":
                            return "age".equals(el1) ? 
                                (!user.isOverAge(Integer.parseInt(el2)) && 
                                !user.isUnderAge(Integer.parseInt(el2)))
                                : user.nameStartsWith(el2);
                        case ">": 
                            return user.isOverAge(Integer.parseInt(el2));
                        case "<": 
                            return user.isUnderAge(Integer.parseInt(el2));
                        case "~": 
                            return user.nameStartsWith(el2);
                        default: 
                            return false;
                    }    
                }
            };
        }};
        
    private Map<String,User> users = new HashMap<>();

    public FakeUsersReadWriteRepository() {
        Faker faker = Faker.instance();
        // Avoid random data to perform consistent tests
        /*for (int k=0; k<faker.random().nextInt(10, 20); k++) {
            User u = UsersFactory.createUserWithDefaultPreferences(
                UserId.randomId(),
                UserName.of("N"+faker.name().fullName()),
                UserAge.of(faker.random().nextInt(18, 120)),
                UserOriginCountry.of(faker.country().name())
            );
            users.put(u.getId().getValue(),u);
        }*/
        users.put("30497182-c376-11ed-afa1-0242ac220002",
            UsersFactory.createUserWithDefaultPreferences(
                UserId.of("30497182-c376-11ed-afa1-0242ac220002"),
                UserName.of("This is a test 1"),
                UserAge.of(20),
                UserOriginCountry.of(faker.country().name())
            ));
        users.put("30497182-c376-11ed-afa1-0242ac120002",
            UsersFactory.createUserWithDefaultPreferences(
                UserId.of("30497182-c376-11ed-afa1-0242ac120002"),
                UserName.of("This is a test 2"),
                UserAge.of(21),
                UserOriginCountry.of(faker.country().name())
            ));
    }

    @Override
    public Iterable<User> findAllUsers() {
        return Collections.unmodifiableCollection(users.values());
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
        users.put(user.getId().getValue(),user);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getId().getValue());
    }

    @Override
    public Iterable<User> findAllUsersSpecification(Object specification, int page, int pageSize) {
        return new PageImpl<>(new ArrayList<User>(users.values().stream()
            .filter(((Specification<User>)specification)::isSatisfied)
            .collect(Collectors.toList())));
    }

    @Override
    public UsersSearchSpecificationService specificationService() {
        return usersSearchSpecificationService;
    }
    
}
