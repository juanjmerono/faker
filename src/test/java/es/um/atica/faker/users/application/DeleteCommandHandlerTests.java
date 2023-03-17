package es.um.atica.faker.users.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.faker.users.application.services.command.DeleteUserCommand;
import es.um.atica.faker.users.application.services.command.DeleteUserCommandHandler;
import es.um.atica.faker.users.domain.event.UserDeleted;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.events.EventBus;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class DeleteCommandHandlerTests {
    
    private static final String ID_USER = "30497182-c376-11ed-afa1-0242ac220002";
    private static final String ID_NOT_EXISTING_USER = "30497182-c376-11ed-afa1-0242ac120555";

    @Autowired
    DeleteUserCommandHandler deleteUserCommandHandler;

    @SpyBean
    UsersWriteRepository usersWriteRepository;

    @SpyBean
    private EventBus eventBus;

    @Test
    public void eliminar_usuario_datos_correctos() throws Exception {
        // Dado un comando delete con id correcto
        DeleteUserCommand del = DeleteUserCommand.of(ID_USER);
        // Cuando se lanza el comando
        deleteUserCommandHandler.handle(del);
        // Entonces se elimina el usuario
        ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
        Mockito.verify(usersWriteRepository).deleteUser(user.capture());
        // Y tiene el id esperado
        assertEquals(ID_USER, user.getValue().getId().toString());
        // Y se lanza el evento
        ArgumentCaptor<UserDeleted> event = ArgumentCaptor.forClass(UserDeleted.class);
        Mockito.verify(eventBus).publish(event.capture());
        assertEquals(ID_USER, event.getValue().getAggregateId());
    }

    @Test
    public void eliminar_usuario_que_no_existe() throws Exception {
        // Dado un comando delete con id inexistente
        DeleteUserCommand del = DeleteUserCommand.of(ID_NOT_EXISTING_USER);
        // Cuando se lanza el comando
        deleteUserCommandHandler.handle(del);
        // Entonces no se elimina ningún usuario
        ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
        Mockito.verify(usersWriteRepository,never()).deleteUser(user.capture());
        // Y tiene el id esperado
        assertEquals(0, user.getAllValues().size());
        // Y no se lanza ningún evento
        ArgumentCaptor<UserDeleted> event = ArgumentCaptor.forClass(UserDeleted.class);
        Mockito.verify(eventBus,never()).publish(event.capture());
        assertEquals(0, event.getAllValues().size());
    }

}

