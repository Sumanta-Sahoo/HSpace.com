package com.hSpace.HSpace.Repository;

import com.hSpace.HSpace.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existEmail(String email);
    Optional<User> findByEmail(String email);
}
