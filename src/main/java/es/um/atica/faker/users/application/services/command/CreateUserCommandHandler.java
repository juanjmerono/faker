package es.um.atica.faker.users.application.services.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.ports.UsersReadRepository;
import es.um.atica.faker.users.application.ports.UsersWriteRepository;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.SyncCommandHandler;
import es.um.atica.shared.domain.events.EventBus;

@Component
public class CreateUserCommandHandler implements SyncCommandHandler<Void,CreateUserCommand>{

    @Autowired
    private UsersWriteRepository usersWriteRepository;

    @Autowired
    private UsersReadRepository usersReadRepository;

    @Autowired
    private EventBus eventBus;

    @Override
    public Void handle(CreateUserCommand command) {
        usersReadRepository.findUser(command.getId())
            .ifPresentOrElse(
                (u)-> { throw new UnsupportedOperationException(u.getId().getValue()); },
                () -> {
                    User usr = User.createUser(UserId.of(command.getId()),UserName.of(command.getName()));
                    usersWriteRepository.saveUser(usr);
                    eventBus.publish(usr);
                }
            );
        return null;        
    }

}
