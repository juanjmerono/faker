package es.um.atica.faker.users.application.services.command;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.CommandHandler;
import es.um.atica.shared.domain.events.EventBus;

@Component
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand>{

    @Autowired
    private UsersWriteRepository usersWriteRepository;

    @Autowired
    private EventBus eventBus;

    @Override
    public void handle(CreateUserCommand command) throws Exception {
        User usr = User.createUser(UUID.fromString(command.getId()),UserName.of(command.getName()));
        usersWriteRepository.saveUser(usr);
        eventBus.publish(usr);
    }
    
}
