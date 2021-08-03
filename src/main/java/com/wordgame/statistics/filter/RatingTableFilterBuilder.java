package com.wordgame.statistics.filter;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.wordgame.core.FilterBuilder;
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
public class RatingTableFilterBuilder extends FilterBuilder<RatingTableFilterForm, RatingTable> {

    @Override
    protected List<Predicate> buildInternal(RatingTableFilterForm filters, Root<RatingTable> root, CriteriaQuery<?> query,
                                            CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();
        if (isNotBlank(filters.getName())) {
            predicates.add(criteriaBuilder.like(root.get(RatingTable_.name), like(filters.getName())));
        }
        if (nonNull(filters.getExpireHoursCycle())) {
            predicates
                .add(criteriaBuilder.equal(root.get(RatingTable_.expireHoursCycle), filters.getExpireHoursCycle()));
        }
        return predicates;
    }
}
