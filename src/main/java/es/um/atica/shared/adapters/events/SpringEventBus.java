package es.um.atica.shared.adapters.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import es.um.atica.shared.domain.events.Event;
import es.um.atica.shared.domain.events.EventBus;

@Component
@org.springframework.context.annotation.Primary
public class SpringEventBus implements EventBus,Supplier<Event> {

    // Queue temporal
    private Queue<Event> queue = new ConcurrentLinkedQueue<>();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
        
    @Override
    public void publish(Event event) {
        // Publish locally
        applicationEventPublisher.publishEvent(event);
        // Store into queue
        queue.add(event);
    }

    @Override
    public Event get() {
        // Send to message broker
        return queue.poll();
    }

}
