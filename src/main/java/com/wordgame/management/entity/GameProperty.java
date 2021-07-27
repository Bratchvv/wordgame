package com.wordgame.management.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "game_properties", schema = "management")
public class GameProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "id_game_properties_generator", sequenceName = "id_game_properties_seq", allocationSize = 50)
    private Long id;

    @Column
    private String name;

    @Column
    private String value;

    @Column
    private String label;
}
