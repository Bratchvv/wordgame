package com.wordgame.statistics.entity;

import com.wordgame.gameplay.entity.Player;
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
@Table(name = "rating_table_data", schema = "statistics")
public class RatingTableData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "id_rating_data_generator", sequenceName = "id_rating_data_seq", allocationSize = 50)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer value;

    @Column
    private Long playerId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_table_id")
    private RatingTable ratingTable;

};