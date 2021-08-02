package com.wordgame.statistics.repository;

import com.wordgame.management.entity.GameWords;
import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.entity.RatingTableData;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingTableRepository extends CrudRepository<RatingTable, Long>,
        JpaSpecificationExecutor<RatingTable> {

    RatingTable findFirstByName(String name);
}
