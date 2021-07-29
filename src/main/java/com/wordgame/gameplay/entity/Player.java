package com.wordgame.gameplay.entity;

import com.wordgame.statistics.entity.RatingTableData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "player", schema = "gameplay")
public class Player {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String urlAvatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "health_id", referencedColumnName = "id")
    private Health health;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Collection<RatingTableData> ratingTableData;
}
