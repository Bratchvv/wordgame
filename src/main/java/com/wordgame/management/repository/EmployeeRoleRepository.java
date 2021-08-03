package com.wordgame.management.repository;

import com.wordgame.management.entity.EmployeeRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

    @Query("Select ur.role.roleName from EmployeeRole ur where ur.employee.id = ?1")
    List<String> getRoleNames(Long id);
}
