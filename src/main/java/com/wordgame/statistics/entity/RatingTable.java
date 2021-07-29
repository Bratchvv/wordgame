package com.wordgame.statistics.entity;

import java.time.LocalDate;
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
@Table(name = "rating_table", schema = "statistics")
public class RatingTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "id_rating_table_generator", sequenceName = "id_rating_table_seq", allocationSize = 50)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate startDate;

    @Column
    private Integer expireDayCount;

    @OneToMany(mappedBy = "ratingTable",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true)
    @ToString.Exclude
    private Collection<RatingTableData> ratingTableData;
}
