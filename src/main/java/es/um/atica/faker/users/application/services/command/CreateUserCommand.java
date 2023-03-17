package es.um.atica.faker.users.application.services.command;

import java.util.UUID;

import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.SyncCommand;

public class CreateUserCommand extends SyncCommand<Void> {
    
    private String id;
    private String name;

    private CreateUserCommand(String id, String name) {
        this.id = id; this.name = name;
    }

    public static CreateUserCommand of(String id, String name) {
        // Validate Command Data for UI
        UserName.of(name);
        UUID.fromString(id);
        return new CreateUserCommand(id, name);
    }

    public String getId() { return id; }
    public String getName() { return name; }

}
