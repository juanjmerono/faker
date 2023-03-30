package es.um.atica.faker;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.ForwardedHeaderFilter;

import es.um.atica.faker.users.adapters.events.UserAllConsumer;
import es.um.atica.faker.users.adapters.events.UserCreatedConsumer;
import es.um.atica.faker.users.adapters.events.UserCreatedConsumerOther;
import es.um.atica.faker.users.adapters.events.UserDeletedConsumer;
import es.um.atica.faker.users.domain.event.UserCreated;
import es.um.atica.faker.users.domain.event.UserDeleted;
import es.um.atica.faker.users.domain.event.UserEvent;
import es.um.atica.shared.adapters.events.SpringEventBus;
import es.um.atica.shared.domain.events.Event;
import es.um.atica.shared.domain.events.EventBus;

@SpringBootApplication
@ComponentScan(basePackages = {"es.um.atica.faker","es.um.atica.shared"})
public class FakerApplication {

	@Autowired
	private SpringEventBus springEventBus;

	@Autowired
	private UserCreatedConsumer userCreatedConsumer;

	@Autowired
	private UserCreatedConsumerOther userCreatedConsumerOther;

	@Autowired
	private UserAllConsumer userAllConsumer;

	@Autowired
	private UserDeletedConsumer userDeletedConsumer;

	@Bean
	public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

	public static void main(String[] args) {
		SpringApplication.run(FakerApplication.class, args);
	}

	@Bean
	public Supplier<Event> eventProducer() {
		return springEventBus;
	}

	@Bean
	public Consumer<UserCreated> createdConsumer() {
		return userCreatedConsumer;
	}

	@Bean
	public Consumer<UserCreated> createdConsumer2() {
		return userCreatedConsumerOther;
	}

	@Bean
	public Consumer<UserEvent> allConsumer() {
		return userAllConsumer;
	}

	@Bean
	public Consumer<UserDeleted> deletedConsumer() {
		return userDeletedConsumer;
	}

	@Bean
	public EventBus eventTypeResolver() {
		return springEventBus;
	}

}
