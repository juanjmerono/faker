package es.um.atica.faker.users.application.command;

import java.util.Optional;

import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.Command;

public class UpdateUserCommand extends Command {
    
    private String id;
    private Optional<String> name;
    private Optional<Integer> age;

    private UpdateUserCommand(String id, Optional<String> name, Optional<Integer> age) {
        this.id = id; this.name = name; this.age = age;
    }

    public static UpdateUserCommand of(String id, Optional<String> name, Optional<Integer> age) {
        // Validate Command Data for UI
        UserId.of(id);
        if (name.isPresent()) UserName.of(name.get());
        if (age.isPresent()) UserAge.of(age.get());
        return new UpdateUserCommand(id, name, age);
    }

    public String getId() { return id; }
    public Optional<String> getName() { return name; }
    public Optional<Integer> getAge() { return age; }

}
