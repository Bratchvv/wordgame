package com.wordgame.admin.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Роли с привязками к пользователям.
 * Т е пересечения пользователь -> его список ролей
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "employee_role", schema = "management")
public class EmployeeRole {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmployeeRole that = (EmployeeRole) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1518071391;
    }
}