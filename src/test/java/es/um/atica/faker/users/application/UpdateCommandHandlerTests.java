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
import es.um.atica.faker.users.application.services.command.UpdateUserCommand;
import es.um.atica.faker.users.application.services.command.UpdateUserCommandHandler;
import es.um.atica.faker.users.domain.event.UserUpdated;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.events.EventBus;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class UpdateCommandHandlerTests {
    
    private static final String ID_USER = "30497182-c376-11ed-afa1-0242ac120002";
    private static final String ID_USER_NAME = "Pepe Lopez";
    private static final String ID_NOT_EXISTING_USER = "30497182-c376-11ed-afa1-0242ac120555";

    @Autowired
    UpdateUserCommandHandler updateUserCommandHandler;

    @SpyBean
    UsersWriteRepository usersWriteRepository;

    @SpyBean
    private EventBus eventBus;

    @Test
    public void actualizar_usuario_datos_correctos() throws Exception {
        // Dado un comando update con id y nombre correcto
        UpdateUserCommand upd = UpdateUserCommand.of(ID_USER,ID_USER_NAME);
        // Cuando se lanza el comando
        updateUserCommandHandler.handle(upd);
        // Entonces se actualiza el usuario
        ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
        Mockito.verify(usersWriteRepository).saveUser(user.capture());
        // Y tiene el id y name esperado
        assertEquals(ID_USER, user.getValue().getId().getValue());
        assertEquals(ID_USER_NAME, user.getValue().getName().getValue());
        // Y se lanza el evento
        ArgumentCaptor<UserUpdated> event = ArgumentCaptor.forClass(UserUpdated.class);
        Mockito.verify(eventBus).publish(event.capture());
        assertEquals(ID_USER, event.getValue().getAggregateId());
    }

    @Test
    public void actualizar_usuario_inexistente() throws Exception {
        // Dado un comando update con id inexistente y nombre correcto
        UpdateUserCommand upd = UpdateUserCommand.of(ID_NOT_EXISTING_USER,ID_USER_NAME);
        // Cuando se lanza el comando
        updateUserCommandHandler.handle(upd);
        // Entonces no se actualiza el usuario
        ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
        Mockito.verify(usersWriteRepository,never()).saveUser(user.capture());
        // Y tiene el id y name esperado
        assertEquals(0, user.getAllValues().size());
        // Y no se lanza el evento
        ArgumentCaptor<UserUpdated> event = ArgumentCaptor.forClass(UserUpdated.class);
        Mockito.verify(eventBus,never()).publish(event.capture());
        assertEquals(0, event.getAllValues().size());
    }

}

