package com.wordgame.admin.model;

import com.wordgame.management.entity.GameCategories;
import com.wordgame.management.entity.GameCategories_;
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
