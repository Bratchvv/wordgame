package com.wordgame.admin.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Описание уникальной роли.
 * Здесь нет привязки к пользователю.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "role", schema = "management")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "role_name", unique=true, length = 30, nullable = false)
    private String roleName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;

        return Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return 1179619963;
    }
}