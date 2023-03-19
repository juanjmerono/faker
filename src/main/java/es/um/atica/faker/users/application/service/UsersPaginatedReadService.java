package es.um.atica.faker.users.application.service;

import es.um.atica.faker.users.domain.model.User;

public interface UsersPaginatedReadService {
 
    // Los conceptos de página y tamaño de página no pertenecen al dominio
    public Iterable<User> findAllUsers(int page, int pageSize);
    
}
