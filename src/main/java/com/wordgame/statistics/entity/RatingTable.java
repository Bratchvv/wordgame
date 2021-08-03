package com.wordgame.statistics.entity;

import java.util.Collection;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Long initTimeUtc;

    @Column
    private Integer expireHoursCycle;

    @OneToMany(mappedBy = "ratingTable",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true)
    @ToString.Exclude
    private Collection<RatingTableData> ratingTableData;

    public Long calcRestoreTime() {
        if(expireHoursCycle == null) {
            return null;
        }
        return (expireHoursCycle*60*60) - ((System.currentTimeMillis() - initTimeUtc)/1000);
    }
}
