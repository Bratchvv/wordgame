package com.wordgame.statistics.repository;

import com.wordgame.statistics.entity.RatingTableData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RatingTableDataRepository extends CrudRepository<RatingTableData, Long> {

    RatingTableData findFirstByNameAndPlayer_Id(String name, String playerId);

    @Transactional
    void deleteAllByRatingTable_Id(Long id);
}
