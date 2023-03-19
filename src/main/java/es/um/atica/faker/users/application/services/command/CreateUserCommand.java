package es.um.atica.faker.users.application.services.command;

import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.SyncCommand;

public class CreateUserCommand extends SyncCommand<Void> {
    
    private String id;
    private String name;
    private int age;

    private CreateUserCommand(String id, String name, int age) {
        this.id = id; this.name = name; this.age = age;
    }

    public static CreateUserCommand of(String id, String name, int age) {
        // Validate Command Data for UI
        UserName.of(name);
        UserId.of(id);
        UserAge.of(age);
        return new CreateUserCommand(id, name, age);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

}
