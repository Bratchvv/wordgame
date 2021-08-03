package com.wordgame.admin.model;

import static java.util.Objects.nonNull;

import com.wordgame.management.entity.GameCategories;
import com.wordgame.management.entity.GameCategories_;
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
public class GameCategoriesFilterFilterBuilder extends FilterBuilder<StoreFilterForm, GameCategories> {

    @Override
    protected List<Predicate> buildInternal(StoreFilterForm filters, Root<GameCategories> root, CriteriaQuery<?> query,
                                            CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();
        if (nonNull(filters.getId())) {
            predicates.add(criteriaBuilder.equal(root.get(GameCategories_.id), filters.getId()));
        }
        return predicates;
    }
}
