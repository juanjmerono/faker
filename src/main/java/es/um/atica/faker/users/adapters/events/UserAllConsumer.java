package es.um.atica.faker.users.adapters.events;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import es.um.atica.faker.users.domain.event.UserEvent;

@Component
public class UserAllConsumer implements Consumer<UserEvent> {

    @Override
    public void accept(UserEvent t) {
        System.err.println(">>>EVENTO USER ????: "+t);
    }
    
}
