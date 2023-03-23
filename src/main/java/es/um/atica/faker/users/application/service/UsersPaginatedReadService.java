package es.um.atica.faker.users.application.service;

import es.um.atica.faker.users.domain.model.User;

public interface UsersPaginatedReadService {
 
    // Debe incluirse un specificaion service
    public UsersSearchSpecificationService specificationService();
    // Los conceptos de página y tamaño de página no pertenecen al dominio
    public Iterable<User> findAllUsers(int page, int pageSize);
    public Iterable<User> findAllUsersSpecification(Object specification, int page, int pageSize);
    
}
