package com.wordgame.management.repository;

import com.wordgame.management.entity.GameCategories;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GameCategoriesRepository extends PagingAndSortingRepository<GameCategories, Long> {

    @Query("from GameCategories where active = true")
    GameCategories getActiveGameCategories();

    @Query("select d.date from GameCategories d where d.active = true")
    LocalDateTime findActiveCategoriesDate();
}
