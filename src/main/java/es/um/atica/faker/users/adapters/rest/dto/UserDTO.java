package es.um.atica.faker.users.adapters.rest.dto;

import es.um.atica.faker.users.domain.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
public class UserDTO {
    @Getter
    private String id;
    @Getter
    private String name;

    public static UserDTO of(User user) {
        return UserDTO
            .builder()
            .id(user.getId().toString())
            .name(user.getName().getValue())
            .build();
    }
}
