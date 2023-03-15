package es.um.atica.faker.users.application.services.command;

import es.um.atica.shared.domain.cqrs.Command;

public class CreateUserCommand extends Command {
    
    private String id;
    private String name;

    private CreateUserCommand(String id, String name) {
        this.id = id; this.name = name;
    }

    public static CreateUserCommand of(String id, String name) {
        return new CreateUserCommand(id, name);
    }

    public String getId() { return id; }
    public String getName() { return name; }

}
