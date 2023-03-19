package es.um.atica.faker.users.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import es.um.atica.faker.users.adapters.rest.dto.UserDTO;

@Component
public class UsersModelAssembler implements RepresentationModelAssembler<UserDTO,EntityModel<UserDTO>> {
    
    private static final String BASE_PATH = "/faker/v1/user";

    @Autowired
    private PagedResourcesAssembler<UserDTO> pagedResourcesAssembler;

    @Override
    public EntityModel<UserDTO> toModel(UserDTO entity) {
        return EntityModel.of(entity,
            linkTo(UsersQueryRestController.class).slash(BASE_PATH + entity.getId()).withSelfRel(),
            linkTo(UsersCommandRestController.class).slash(BASE_PATH + entity.getId()).withRel("put"),
            linkTo(UsersCommandRestController.class).slash(BASE_PATH + entity.getId()).withRel("delete"));
    }

    @Override
    public CollectionModel<EntityModel<UserDTO>> toCollectionModel(Iterable<? extends UserDTO> entities) {
        PagedModel<EntityModel<UserDTO>> model = pagedResourcesAssembler.toModel((Page)entities, this);
        try {
            model.add(linkTo(methodOn(UsersCommandRestController.class).createUser(null, null, null, null)).withRel("post"));
        } catch (Exception ex) {}
        return model; 

    }

}