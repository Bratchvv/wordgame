package com.wordgame.management.repository;

import com.wordgame.management.entity.GameProperty;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

@Repository
@Transactional
public interface GamePropertyRepository extends CrudRepository<GameProperty, Long> {

    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    Optional<GameProperty> findByName(String name);

    List<GameProperty> findAll();
}