package com.wordgame.gameplay.repository;

import com.wordgame.gameplay.entity.PlayerGameCategories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PlayerGameCategoriesRepository extends CrudRepository<PlayerGameCategories, Long> {

    PlayerGameCategories findFirstByPlayer_Id(String id);
}
