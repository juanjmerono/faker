package es.um.atica.shared.domain.specification;

public interface Specification<T> {

    boolean isSatisfied(T object);

    static <T> T[] prepend(T[] arr, T firstElement) {
        final int N = arr.length;
        arr = java.util.Arrays.copyOf(arr, N+1);
        System.arraycopy(arr, 0, arr, 1, N);
        arr[0] = firstElement;
        return arr;
    }    

    default Specification<T> and(Specification<T>... specifications) {
        return new AndSpecification(prepend(specifications,this));
    }

    default Specification<T> or(Specification<T>... specifications) {
        return new OrSpecification(prepend(specifications,this));
    }

    default Specification<T> not(Specification<T> specification) {
        return new NotSpecification(specification);
    }

}
