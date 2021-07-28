package com.wordgame.gameplay.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "health", schema = "gameplay")
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "id_health_generator", sequenceName = "id_player_seq", allocationSize = 50)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Integer lifes;

    @Column
    private Long secondsRestoreLife;

    @Column
    private Long timeUTCSaving;

    @Column
    private Long timeUTCNow;

    @OneToOne(mappedBy = "health", cascade = CascadeType.ALL)
    private Player player;
}
