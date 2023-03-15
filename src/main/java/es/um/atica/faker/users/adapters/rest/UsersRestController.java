package es.um.atica.faker.users.adapters.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRestController {
    
    @GetMapping("/users")
    @PreAuthorize("hasPermission(#jwt, 'GET_USERS')")
    public String listaUsuarios(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getSubject();
    }

}
