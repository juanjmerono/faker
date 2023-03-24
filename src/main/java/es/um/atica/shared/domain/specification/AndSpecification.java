package es.um.atica.shared.domain.specification;

import java.util.Arrays;
import java.util.List;

public class AndSpecification<T> implements Specification<T> {

    private List<Specification<T>> specifications;

    public AndSpecification(Specification<T>... specifications) {
        this.specifications = Arrays.asList(specifications);
    }

    public boolean isSatisfied(T object) {
        return specifications.stream().filter(s-> s!=null).allMatch(s -> s.isSatisfied(object) );
    }
}
