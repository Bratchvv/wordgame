package com.wordgame.management.repository;

import com.wordgame.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("from Employee a where a.login = ?1")
    Optional<Employee> findUserAccount(String employeeName);
}