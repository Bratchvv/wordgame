package com.wordgame.management.repository;

import com.wordgame.management.dto.GameWordsInfoDto;
import com.wordgame.management.entity.GameCategories;
import com.wordgame.management.entity.GameWords;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GameCategoriesRepository extends CrudRepository<GameCategories, Long> {

}
