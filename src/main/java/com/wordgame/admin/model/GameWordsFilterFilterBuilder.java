package com.wordgame.admin.model;

import com.wordgame.management.entity.GameWords;
import com.wordgame.management.entity.GameWords_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Vladimir Bratchikov
 */
@Component
public class GameWordsFilterFilterBuilder extends FilterBuilder<StoreFilterForm, GameWords> {

    @Override
    protected List<Predicate> buildInternal(StoreFilterForm filters, Root<GameWords> root, CriteriaQuery<?> query,
                                            CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();
        if (isNotBlank(filters.getName())) {
            predicates.add(criteriaBuilder.like(root.get(GameWords_.name), like(filters.getName())));
        }
        if (nonNull(filters.getId())) {
            predicates.add(criteriaBuilder.equal(root.get(GameWords_.id), filters.getId()));
        }
        return predicates;
    }
}
