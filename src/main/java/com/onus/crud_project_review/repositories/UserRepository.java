package com.onus.crud_project_review.repositories;

import com.onus.crud_project_review.entities.Users;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
    boolean existsByEmail(@NotEmpty(message = "email should not be empty") String email);
}
