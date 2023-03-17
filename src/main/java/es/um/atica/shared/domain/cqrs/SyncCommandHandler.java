package es.um.atica.shared.domain.cqrs;

public interface SyncCommandHandler<T,U extends SyncCommand<T>> {
    T handle(U command) throws Exception;
}