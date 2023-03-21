package es.um.atica.faker.users.application.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.um.atica.faker.users.application.service.UsersOriginCountryService;
import es.um.atica.faker.users.domain.factory.UsersFactory;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.faker.users.domain.model.UserOriginCountry;
import es.um.atica.faker.users.domain.repository.UsersReadRepository;
import es.um.atica.faker.users.domain.repository.UsersWriteRepository;
import es.um.atica.shared.domain.cqrs.SyncCommandHandler;
import es.um.atica.shared.domain.events.EventBus;

@Component
public class CreateUserCommandHandler implements SyncCommandHandler<Void,CreateUserCommand>{

    @Autowired
    private UsersWriteRepository usersWriteRepository;

    @Autowired
    private UsersReadRepository usersReadRepository;

    @Autowired
    private UsersOriginCountryService usersOriginCountryService;

    @Autowired
    private EventBus eventBus;

    @Override
    public Void handle(CreateUserCommand command) {
        // Idempotency
        usersReadRepository.findUser(command.getId())
            .ifPresentOrElse(
                (u)-> { throw new UnsupportedOperationException(String.format("Usuario ya creado %s",u.getId().getValue())); },
                () -> {
                    User usr = UsersFactory
                        .createUserWithDefaultPreferences(
                            UserId.of(command.getId()),
                            UserName.of(command.getName()),
                            UserAge.of(command.getAge()),
                            UserOriginCountry.of(usersOriginCountryService.getOriginCountry(command.getIPAddress())));
                    usr.createUser();
                    usersWriteRepository.saveUser(usr);
                    eventBus.publish(usr);
                }
            );
        return null;        
    }

}
