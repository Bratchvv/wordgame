package com.wordgame.admin.model;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * @param <F> entity
 * @param <E> filter dto
 *
 * @author vbratchikov
 */
public abstract class FilterBuilder<F, E> {

    public Specification<E> build(F filter) {
        if (isNull(filter)) {
            return (root, query, cb) -> null;
        }
        return (root, query, cb) -> {
            List<Predicate> predicates = buildInternal(filter, root, query, cb);
            if (isEmpty(predicates)) {
                return null;
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    protected abstract List<Predicate> buildInternal(F filter, Root<E> root, CriteriaQuery<?> query,
                                                     CriteriaBuilder cb);

    protected String likeStartWith(String value) {
        return value + "%";
    }

    protected String like(String value) {
        return "%" + likeStartWith(value);
    }
}
