package com.Fintech.fintech.project.repository;

import com.Fintech.fintech.project.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(@NotBlank(message = "User name is cannot be empty") String name);
}
