package es.um.atica.faker.users.adapters.rest;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.faker.users.adapters.rest.dto.UserDTO;
import es.um.atica.faker.users.application.query.GetUserQuery;
import es.um.atica.faker.users.application.query.GetUsersQuery;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.cqrs.QueryBus;

@RestController
@RequestMapping(value="/faker/v1")
public class UsersQueryRestController {
    
    private static final String PAGE_SIZE = "5";

    @Autowired
    private QueryBus queryBus;

    @Autowired
    private UsersModelAssembler usersModelAssembler;

    @GetMapping("/user")
    @PreAuthorize("hasPermission(#jwt, 'GET_USERS')")
    public CollectionModel<EntityModel<UserDTO>> allUsers(@AuthenticationPrincipal Jwt jwt,
        @RequestParam(name="page",required = false, defaultValue = "0") int page,
		@RequestParam(name="size",required = false, defaultValue = PAGE_SIZE) int page_size) throws Exception {
        Page<User> pageUser = (Page<User>)queryBus.handle(GetUsersQuery.of(page,page_size));
        return usersModelAssembler
            .toCollectionModel(
                new PageImpl<UserDTO>(StreamSupport.stream(pageUser.spliterator(), false)
                        .map(UserDTO::of)
                        .collect(Collectors.toList()),
                    pageUser.getPageable(),
                    pageUser.getTotalElements()));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasPermission(#jwt, 'GET_USERS')")
    public EntityModel<UserDTO> userDetail(@AuthenticationPrincipal Jwt jwt, 
        @PathVariable(name="id",required = true) String userId) throws Exception {
        return usersModelAssembler.toModel(queryBus.handle(GetUserQuery.of(userId)).map(UserDTO::of).orElseThrow());
    }

}
