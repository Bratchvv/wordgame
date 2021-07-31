package com.wordgame.management.repository;

import com.wordgame.management.dto.GameWordsInfoDto;
import com.wordgame.management.entity.GameWords;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GameWordsRepository extends PagingAndSortingRepository<GameWords, Long> {

    @Query(value = "SELECT new com.wordgame.management.dto.GameWordsInfoDto(id, name, date, category, active) FROM GameWords ")
    List<GameWordsInfoDto> findAllInfo();

    @Query(value = "SELECT new com.wordgame.management.dto.GameWordsInfoDto(id, name, date, category, active) FROM GameWords " +
            "WHERE active = true and category = :category")
    GameWordsInfoDto findActiveInfo(Integer category);

    GameWords findByCategoryAndActive(Integer category, boolean active);
}
