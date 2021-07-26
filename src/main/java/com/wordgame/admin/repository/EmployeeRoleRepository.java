package com.wordgame.admin.repository;

import com.wordgame.admin.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

    @Query("Select ur.role.roleName from EmployeeRole ur where ur.employee.id = ?1")
    List<String> getRoleNames(Long id);
}
