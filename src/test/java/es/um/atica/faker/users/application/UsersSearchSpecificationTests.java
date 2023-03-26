package es.um.atica.faker.users.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import es.um.atica.faker.users.adapters.fake.FakeUsersReadWriteRepository;
import es.um.atica.faker.users.adapters.jpa.DBUsersPaginatedReadRepository;
import es.um.atica.faker.users.domain.model.User;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class UsersSearchSpecificationTests {
    
    //@Autowired
    //private UsersPaginatedReadService usersPaginatedReadService;

    @Autowired
    private FakeUsersReadWriteRepository fakeUsersReadWriteRepository;

    @Autowired
    private DBUsersPaginatedReadRepository dbUsersPaginatedReadRepository;

    private static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of(Arrays.asList("age>20"),0,5,1),
            Arguments.of(Arrays.asList("age>121"),0,5,0),
            Arguments.of(Arrays.asList("age>121|age<18"),0,5,0),
            Arguments.of(Arrays.asList("name~This"),0,5,2),
            Arguments.of(Arrays.asList("age>20","name~This"),0,5,1),
            Arguments.of(Arrays.asList("age>18"),0,5,2),
            Arguments.of(Arrays.asList("age<18|age>18","name:A|name~This"),0,5,2),
            Arguments.of(Arrays.asList("name:Pepe|age>18"),0,5,2),
            Arguments.of(Arrays.asList("age>18","name~Ok"),0,5,0),
            Arguments.of(Arrays.asList("age:20","name:This is a test 1"),0,5,1),
            Arguments.of(Arrays.asList("age:20","name:NO|name:This is a test 1"),0,5,1)
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void check_faker_query_results(List<String> search,int page, int pageSize, int expected) {
        Iterable<User> users = fakeUsersReadWriteRepository.findAllUsersSpecification(
            fakeUsersReadWriteRepository.specificationService()
                .buildSpecificationFromSearch(search),
            page, pageSize);
        assertEquals(expected,StreamSupport.stream(users.spliterator(), false).count());
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void check_db_query_results(List<String> search,int page, int pageSize, int expected) {
        Iterable<User> users = dbUsersPaginatedReadRepository.findAllUsersSpecification(
            dbUsersPaginatedReadRepository.specificationService()
                .buildSpecificationFromSearch(search),
            page, pageSize);
        assertEquals(expected,StreamSupport.stream(users.spliterator(), false).count());
    }

}
