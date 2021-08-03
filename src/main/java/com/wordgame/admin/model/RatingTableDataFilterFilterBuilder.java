package com.wordgame.admin.model;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.wordgame.gameplay.entity.Player_;
import com.wordgame.statistics.entity.RatingTableData;
import com.wordgame.statistics.entity.RatingTableData_;
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
public class RatingTableDataFilterFilterBuilder extends FilterBuilder<StoreFilterForm, RatingTableData> {

    @Override
    protected List<Predicate> buildInternal(StoreFilterForm filters, Root<RatingTableData> root, CriteriaQuery<?> query,
                                            CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();
        if (isNotBlank(filters.getName())) {
            predicates.add(criteriaBuilder.equal(root.get(RatingTableData_.name), filters.getName()));
        }
        if (isNotBlank(filters.getPlayerName())) {
            predicates.add(criteriaBuilder.like(root.get(RatingTableData_.player).get(Player_.name),
                                                like(filters.getPlayerName())));
        }
        if (isNotBlank(filters.getPlayerId())) {
            predicates
                .add(criteriaBuilder.equal(root.get(RatingTableData_.player).get(Player_.id), filters.getPlayerId()));
        }
        return predicates;
    }
}
