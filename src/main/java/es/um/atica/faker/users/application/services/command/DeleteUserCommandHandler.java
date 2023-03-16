package es.um.atica.faker.users.application.services.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.ports.UsersReadRepository;
import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.shared.domain.cqrs.CommandHandler;
import es.um.atica.shared.domain.events.EventBus;

@Component
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand>{

    @Autowired
    private UsersReadRepository usersReadRepository;

    @Autowired
    private UsersWriteRepository usersWriteRepository;

    @Autowired
    private EventBus eventBus;

    @Override
    public void handle(DeleteUserCommand command) throws Exception {
        usersReadRepository.findUser(command.getId().toString()).ifPresent((u)->{
            u.deleteUser();
            usersWriteRepository.deleteUser(u);
            eventBus.publish(u);
        });
    }
    
}
