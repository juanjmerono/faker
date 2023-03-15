package es.um.atica.faker;

import java.io.Serializable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class FakerPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return (authentication.getPrincipal() instanceof Jwt) && "admin@acme.es".equals(((Jwt)authentication.getPrincipal()).getSubject());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return (authentication.getPrincipal() instanceof Jwt) && "admin@acme.es".equals(((Jwt)authentication.getPrincipal()).getSubject());
    }
    
}
