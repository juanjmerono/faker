package es.um.atica.shared.domain.specification;

public interface Specification<T> {

    boolean isSatisfied(T object);

    default Object getBaseSpecification() {
        return this;
    }

    default Specification<T> and(Specification<T>... specifications) {
        return new AndSpecification(specifications);
    }

    default Specification<T> or(Specification<T>... specifications) {
        return new OrSpecification(specifications);
    }

    default Specification<T> not(Specification<T> specification) {
        return new NotSpecification(specification);
    }
}
