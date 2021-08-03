package com.wordgame.admin.model;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.entity.RatingTable_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Bratchikov
 */
@Component
public class RatingTableFilterFilterBuilder extends FilterBuilder<StoreFilterForm, RatingTable> {

    @Override
    protected List<Predicate> buildInternal(StoreFilterForm filters, Root<RatingTable> root, CriteriaQuery<?> query,
                                            CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();
        if (isNotBlank(filters.getName())) {
            predicates.add(criteriaBuilder.like(root.get(RatingTable_.name), like(filters.getName())));
        }
        if (nonNull(filters.getId())) {
            predicates
                .add(criteriaBuilder.equal(root.get(RatingTable_.expireHoursCycle), filters.getExpireHoursCycle()));
        }
        return predicates;
    }
}
