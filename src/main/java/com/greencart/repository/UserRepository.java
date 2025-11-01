package com.greencart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.greencart.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
