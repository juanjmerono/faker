package es.um.atica.faker.users.application.services.command;

import java.util.UUID;

import es.um.atica.shared.domain.cqrs.Command;

public class DeleteUserCommand extends Command {
    
    private String id;

    private DeleteUserCommand(String id) {
        this.id = id;
    }

    public static DeleteUserCommand of(String id) {
        // Validate Command Data for UI
        UUID.fromString(id);
        return new DeleteUserCommand(id);
    }

    public String getId() { return id; }

}
