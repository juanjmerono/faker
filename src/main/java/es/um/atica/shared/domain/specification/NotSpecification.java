package es.um.atica.shared.domain.specification;

public class NotSpecification<T> implements Specification<T> {

    private Specification<T> specification;

    public NotSpecification(Specification<T> specification) {
        this.specification = specification;
    }

    public boolean isSatisfied(T object) {
        return !specification.isSatisfied(object);
    }
}