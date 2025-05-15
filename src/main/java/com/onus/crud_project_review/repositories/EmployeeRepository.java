package com.onus.crud_project_review.repositories;

import com.onus.crud_project_review.entities.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employees, String> {
    boolean existsByEmail(String email);
    Employees findByEmail(String email);
}
