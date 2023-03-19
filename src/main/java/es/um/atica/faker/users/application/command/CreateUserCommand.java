package es.um.atica.faker.users.application.command;

import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.shared.domain.cqrs.SyncCommand;

public class CreateUserCommand extends SyncCommand<Void> {
    
    private static final String IP_REGEXP = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

    private String id;
    private String name;
    private int age;
    private String ipAddress;

    private CreateUserCommand(String id, String name, int age, String ipAddress) {
        this.id = id; this.name = name; this.age = age; this.ipAddress = ipAddress;
    }

    public static CreateUserCommand of(String id, String name, int age, String ipAddress) {
        // Validate Command Data for UI
        UserName.of(name);
        UserId.of(id);
        UserAge.of(age);
        if (!ipAddress.matches(IP_REGEXP)) throw new IllegalArgumentException("You should use a valid ip address!");
        return new CreateUserCommand(id, name, age, ipAddress);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getIPAddress() { return ipAddress; }

}
