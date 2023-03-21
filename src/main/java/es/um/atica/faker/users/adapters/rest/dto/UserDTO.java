package es.um.atica.faker.users.adapters.rest.dto;

import org.springframework.hateoas.server.core.Relation;

import es.um.atica.faker.users.domain.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDTO {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private Integer age;

    public static UserDTO of(User user) {
        return UserDTO
            .builder()
            .id(user.getId().getValue())
            .name(user.getName().getValue())
            .age(user.getAge().getValue())
            .build();
    }
}
