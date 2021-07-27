package com.wordgame.management.service;


import com.wordgame.management.entity.Employee;
import com.wordgame.management.repository.EmployeeRepository;
import com.wordgame.management.repository.EmployeeRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Собственный сервис который переопределяет базовые спринговые методы по авторизации.
 * Такой подход нужен для использования общего механизма spring security,
 * но с учетом нашей инфраструктуры БД, особенностей наших юзеров и т д.
 */
@Service
@AllArgsConstructor
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeRoleRepository employeeRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String employeeName) throws UsernameNotFoundException {
        // Работа с нашей БД!
        Employee employee = this.employeeRepository.findUserAccount(employeeName)
            .orElseThrow(RuntimeException::new);

        if (employee == null) {
            System.out.println("Employee not found! " + employeeName);
            throw new UsernameNotFoundException("User " + employeeName + " was not found in the database");
        }

        System.out.println("Found employee: " + employee);

        // Работа с нашей БД!
        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.employeeRoleRepository.getRoleNames(employee.getId());

        // Работа с Spring User
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                employee.getLogin(),employee.getPassword(), grantList);

        return userDetails;
    }

}