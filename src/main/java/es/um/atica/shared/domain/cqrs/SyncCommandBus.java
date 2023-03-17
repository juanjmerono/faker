package es.um.atica.shared.domain.cqrs;

public interface SyncCommandBus {
    <T> T handle(SyncCommand<T> command) throws Exception;
}
