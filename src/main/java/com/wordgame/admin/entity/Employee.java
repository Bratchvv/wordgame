package com.wordgame.admin.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "employee", schema = "management")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique=true, length = 36, nullable = false)
    private String login;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 1, nullable = false)
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Employee employee = (Employee) o;

        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return 949447908;
    }
}