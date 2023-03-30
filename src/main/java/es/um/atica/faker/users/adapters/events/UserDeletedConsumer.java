package es.um.atica.faker.users.adapters.events;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import es.um.atica.faker.users.domain.event.UserDeleted;

@Component
public class UserDeletedConsumer implements Consumer<UserDeleted> {

    @Override
    public void accept(UserDeleted t) {
        System.err.println(">>>EVENTO USER DELETED: "+t);
    }
    
}
