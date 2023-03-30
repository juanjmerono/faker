package es.um.atica.faker.users.domain.event;

import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.shared.domain.events.Event;

public class UserEvent extends Event {

    protected UserId userId;

    public UserEvent() { super(); }

    protected UserEvent(UserId id) {
        super();
        this.userId = id;
    }

    @Override
    public String getAggregateId() {
        return this.userId.getValue();
    }

    @Override
    public String getTypeFormat() { return "faker.events.1.%s"; }

    @Override
    public String toString() { 
        return super.toString() + String.format("[userId:%s]",userId);
    }

}
