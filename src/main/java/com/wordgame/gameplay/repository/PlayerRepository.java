package com.wordgame.gameplay.repository;

import com.wordgame.gameplay.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

}
