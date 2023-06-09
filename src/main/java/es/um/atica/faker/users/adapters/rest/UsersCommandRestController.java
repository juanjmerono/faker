package es.um.atica.faker.users.adapters.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.faker.users.adapters.rest.dto.UserDTO;
import es.um.atica.faker.users.application.command.CreateUserCommand;
import es.um.atica.faker.users.application.command.DeleteUserCommand;
import es.um.atica.faker.users.application.command.UpdateUserCommand;
import es.um.atica.shared.domain.cqrs.CommandBus;
import es.um.atica.shared.domain.cqrs.SyncCommandBus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users Endpoints")
@RestController
@RequestMapping(value="/faker/v1")
public class UsersCommandRestController {
    
    @Autowired
    private CommandBus commandBus;

    @Autowired
    private SyncCommandBus syncCommandBus;

    @Autowired
    private UsersModelAssembler usersModelAssembler;

    @Operation(
        description = "Create new user",
        responses = {
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "403", ref = "forbidden_post"),
            @ApiResponse(responseCode = "409", ref = "conflict"),
            @ApiResponse(responseCode = "200", ref = "ok_user"),
        }
    )
    @PostMapping("/user/{id}")
    @PreAuthorize("hasPermission(#jwt, 'CREATE_USERS')")
    public EntityModel<UserDTO> createUser(@AuthenticationPrincipal Jwt jwt, 
        @PathVariable(name="id",required = true) String userId,
        @RequestBody UserDTO usr,
        HttpServletRequest req) throws Exception {
        syncCommandBus.handle(CreateUserCommand.of(userId, usr.getName(), usr.getAge(), req.getRemoteAddr()));
        return usersModelAssembler.toModel(UserDTO.builder().id(userId).build());
    }

    @Operation(
        description = "Update existing user",
        responses = {
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "403", ref = "forbidden_put"),
            @ApiResponse(responseCode = "200", ref = "ok_user"),
        }
    )
    @PutMapping("/user/{id}")
    @PreAuthorize("hasPermission(#jwt, 'UPDATE_USERS')")
    public EntityModel<UserDTO> updateUser(@AuthenticationPrincipal Jwt jwt, 
        @PathVariable(name="id",required = true) String userId,
        @RequestBody UserDTO usr) throws Exception {
        commandBus.handle(UpdateUserCommand.of(userId, Optional.ofNullable(usr.getName()), Optional.ofNullable(usr.getAge())));
        return usersModelAssembler.toModel(UserDTO.builder().id(userId).build());
    }

    @Operation(
        description = "Delete existing user",
        responses = {
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "403", ref = "forbidden_delete"),
            @ApiResponse(responseCode = "200", ref = "ok_user"),
        }
    )
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasPermission(#jwt, 'DELETE_USERS')")
    public EntityModel<UserDTO> deleteUser(@AuthenticationPrincipal Jwt jwt, 
        @PathVariable(name="id",required = true) String userId) throws Exception {
        commandBus.handle(DeleteUserCommand.of(userId));
        return usersModelAssembler.toModel(UserDTO.builder().id(userId).build());
    }

}
