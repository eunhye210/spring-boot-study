package com.eunhye.onus_crud_3.repositories;

import com.eunhye.onus_crud_3.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByEmail(String email);
    Employee findByEmail(String email);
}
