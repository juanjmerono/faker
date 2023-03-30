package es.um.atica.faker.users.adapters.events;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import es.um.atica.faker.users.domain.event.UserCreated;

@Component
public class UserCreatedConsumer implements Consumer<UserCreated> {

    @Override
    public void accept(UserCreated t) {
        System.err.println(">>>EVENTO USER CREATED: "+t);
    }
    
}
