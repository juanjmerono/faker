package es.um.atica.faker.users.adapters.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import es.um.atica.faker.users.application.service.UsersSearchSpecificationService;

@Service
public class JPAUsersSpecification implements UsersSearchSpecificationService {

    @Override
    public Object defaultSpec() {
        return new org.springframework.data.jpa.domain.Specification<UserEntity>(){
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query,CriteriaBuilder criteriaBuilder) {
                // BY default all users have more tha 18
                return criteriaBuilder.gt(root.get("age"),18);
            }};
    }

    @Override
    public Object buildAndSpec(Object element1, Object element2) {
        return ((Specification<UserEntity>)element1).and((Specification<UserEntity>)element2);
    }

    @Override
    public Object buildOrSpec(Object element1, Object element2) {
        return ((Specification<UserEntity>)element1).or((Specification<UserEntity>)element2);
    }

    @Override
    public Object buildSpecFor(String el1, String op, String el2) {
        return new org.springframework.data.jpa.domain.Specification<UserEntity>(){
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                switch(op) {
                    case ":": 
                        return criteriaBuilder.equal(root.get(el1),"age".equals(el1)?Integer.parseInt(el2):el2);
                    case ">": 
                        return criteriaBuilder.gt(root.get(el1),Integer.parseInt(el2));
                    case "<": 
                        return criteriaBuilder.lt(root.get(el1),Integer.parseInt(el2));
                    case "~": 
                        return criteriaBuilder.like(root.get(el1),el2 + "%");
                    default: 
                        return null;
                }                
            }
        };
    }
    
}
