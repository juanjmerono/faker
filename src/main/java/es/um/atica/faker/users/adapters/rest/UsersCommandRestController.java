package es.um.atica.faker.users.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.faker.users.adapters.rest.dto.UserDTO;
import es.um.atica.shared.domain.cqrs.CommandBus;

@RestController
@RequestMapping(value="/faker/v1")
public class UsersCommandRestController {
    
    @Autowired
    private CommandBus commandBus;
    
    @Autowired
    private UsersModelAssembler usersModelAssembler;

    @PostMapping("/user/{id}")
    @PreAuthorize("hasPermission(#jwt, 'CREATE_USERS')")
    public EntityModel<UserDTO> createUser(@AuthenticationPrincipal Jwt jwt, 
        @PathVariable(name="id",required = true) String userId) {
        //commandBus.handle(null);
        return usersModelAssembler.toModel(UserDTO.builder().id(userId).build());
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasPermission(#jwt, 'UPDATE_USERS')")
    public EntityModel<UserDTO> updateUser(@AuthenticationPrincipal Jwt jwt, 
        @PathVariable(name="id",required = true) String userId) {
        //commandBus.handle(null);
        return usersModelAssembler.toModel(UserDTO.builder().id(userId).build());
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasPermission(#jwt, 'DELETE_USERS')")
    public EntityModel<UserDTO> deleteUser(@AuthenticationPrincipal Jwt jwt, 
        @PathVariable(name="id",required = true) String userId) {
        //commandBus.handle(null);
        return usersModelAssembler.toModel(UserDTO.builder().id(userId).build());
    }

}
