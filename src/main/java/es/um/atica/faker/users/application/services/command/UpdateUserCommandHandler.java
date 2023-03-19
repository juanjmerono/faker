package es.um.atica.faker.users.application.services.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.ports.UsersReadRepository;
import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.CommandHandler;
import es.um.atica.shared.domain.events.EventBus;

@Component
public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand>{

    @Autowired
    private UsersReadRepository usersReadRepository;

    @Autowired
    private UsersWriteRepository usersWriteRepository;

    @Autowired
    private EventBus eventBus;

    @Override
    public void handle(UpdateUserCommand command) {
        // Idempotency
        usersReadRepository.findUser(command.getId()).ifPresent((u)->{
            if (command.getName().isPresent()) u.updateUserName(UserName.of(command.getName().get()));
            if (command.getAge().isPresent()) u.updateUserAge(UserAge.of(command.getAge().get()));
            usersWriteRepository.saveUser(u);
            eventBus.publish(u);
        });
    }
    
}
