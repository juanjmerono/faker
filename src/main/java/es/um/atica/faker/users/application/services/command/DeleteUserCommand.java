package es.um.atica.faker.users.application.services.command;

import es.um.atica.shared.domain.cqrs.Command;

public class DeleteUserCommand extends Command {
    
    private String id;

    private DeleteUserCommand(String id) {
        this.id = id;
    }

    public static DeleteUserCommand of(String id) {
        return new DeleteUserCommand(id);
    }

    public String getId() { return id; }

}
