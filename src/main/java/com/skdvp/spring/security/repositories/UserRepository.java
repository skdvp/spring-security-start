package com.skdvp.spring.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skdvp.spring.security.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
