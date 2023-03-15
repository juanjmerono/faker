package es.um.atica.shared.domain.cqrs;

public interface QueryBus {
    <T> T handle(Query<T> query) throws Exception;
}
