package es.um.atica.shared.domain.cqrs;

public interface CommandBus {
    void handle(Command command) throws Exception;
}
