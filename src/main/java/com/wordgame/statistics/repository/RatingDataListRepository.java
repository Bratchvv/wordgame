package com.wordgame.statistics.repository;

import com.wordgame.statistics.entity.RatingTableData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingDataListRepository extends PagingAndSortingRepository<RatingTableData, Long> {

    List<RatingTableData> findAllByName(String name, Pageable pageable);
    List<RatingTableData> findAllByPlayerId(Long playerId, Pageable pageable);
}
