package es.um.atica.faker.users.application.services.command;

import java.util.UUID;

import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.Command;

public class UpdateUserCommand extends Command {
    
    private String id;
    private String name;

    private UpdateUserCommand(String id, String name) {
        this.id = id; this.name = name;
    }

    public static UpdateUserCommand of(String id, String name) {
        // Validate Command Data for UI
        UserName.of(name);
        UUID.fromString(id);
        return new UpdateUserCommand(id, name);
    }

    public String getId() { return id; }
    public String getName() { return name; }

}
