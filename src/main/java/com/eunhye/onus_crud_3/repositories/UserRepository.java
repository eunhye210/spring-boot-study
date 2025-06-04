package com.eunhye.onus_crud_3.repositories;

import com.eunhye.onus_crud_3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
