package com.wordgame.management.entity;

import java.time.LocalDateTime;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "game_words", schema = "management")
public class GameWords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "id_game_words_generator",
        sequenceName = "id_game_words_seq", allocationSize = 50)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDateTime date;

    @Column
    private String data;

    @Column
    private boolean active;
}
