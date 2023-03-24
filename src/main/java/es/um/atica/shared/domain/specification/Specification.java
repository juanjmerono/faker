package es.um.atica.shared.domain.specification;

public interface Specification<T> {

    boolean isSatisfied(T object);

}
