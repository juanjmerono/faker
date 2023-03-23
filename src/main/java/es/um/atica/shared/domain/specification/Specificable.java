package es.um.atica.shared.domain.specification;

public interface Specificable<T> {
    boolean satisfies(Specification<T> object);
}