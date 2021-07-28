package com.wordgame.statistics.repository;

import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.entity.RatingTableData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingTableDataRepository extends CrudRepository<RatingTableData, Long> {

    RatingTableData findFirstByNameAndPlayerId(String name, Long playerId);
}
