package com.wordgame.statistics.repository;

import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.entity.RatingTableData;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RatingTableDataRepository extends CrudRepository<RatingTableData, Long>,
        JpaSpecificationExecutor<RatingTableData> {

    RatingTableData findFirstByNameAndPlayer_Id(String name, String playerId);

    @Transactional
    void deleteAllByRatingTable_Id(Long id);

    @Query(value = "SELECT row_number() OVER (ORDER BY value desc) AS i," +
            " e.player_id as player, e.value as value " +
            " FROM statistics.rating_table_data e" +
            " WHERE e.name=:name ORDER BY e.value desc limit :limit",
            nativeQuery = true)
    List<IndexedRatingDto> ratingTop(@Param("name") String name,
                                     @Param("limit") long limit);

    @Query(value = "SELECT i as i, r.player_id  as player, r.value as value FROM " +
            "(SELECT row_number() OVER (ORDER BY value desc) AS i, e.player_id, e.value" +
            " FROM statistics.rating_table_data e" +
            " WHERE e.name=:name ORDER BY e.value desc" +
            " ) as r WHERE r.player_id = :playerId",
            nativeQuery = true)
    IndexedRatingDto ratingTopPlayer(@Param("name") String name, @Param("playerId") String playerId);


    @Query(value = "SELECT i as i, r.player_id  as player, r.value as value FROM " +
            "(SELECT row_number() OVER (ORDER BY value desc) AS i, e.player_id, e.value" +
            " FROM statistics.rating_table_data e" +
            " WHERE e.name=:name ORDER BY e.value desc" +
            " ) as r WHERE r.i < :current and r.i >= :upper",
            nativeQuery = true)
    List<IndexedRatingDto> ratingTopUpper(@Param("name") String name,
                                     @Param("current") long current,
                                     @Param("upper") long upper);

    @Query(value = "SELECT i as i, r.player_id  as player, r.value as value FROM " +
            "(SELECT row_number() OVER (ORDER BY value desc) AS i, e.player_id, e.value" +
            " FROM statistics.rating_table_data e" +
            " WHERE e.name=:name ORDER BY e.value desc" +
            " ) as r WHERE r.i > :current and r.i <= :lower",
            nativeQuery = true)
    List<IndexedRatingDto> ratingTopLower(@Param("name") String name,
                                     @Param("current") long current,
                                     @Param("lower") long lower);

    interface IndexedRatingDto {
        long getI();
        String getPlayer();
        long getValue();
    }
}
