package es.um.atica.shared.domain.cqrs;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
