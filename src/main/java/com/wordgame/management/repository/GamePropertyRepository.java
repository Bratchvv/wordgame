package com.wordgame.management.repository;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

import com.wordgame.management.entity.GameProperty;
import java.util.List;
import java.util.Optional;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GamePropertyRepository extends CrudRepository<GameProperty, Long> {

    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    Optional<GameProperty> findByName(String name);

    List<GameProperty> findAll();
}
